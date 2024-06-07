package com.mphong.EMWebAPI.Utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;

@Service
public class JwtUtil {
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            keyGen.init(256); // Key size
            this.secretKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing secret key", e);
        }
    }

    public String generateToken(UserDetails userDetails) {
        try {
            JWSSigner signer = new MACSigner(secretKey);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userDetails.getUsername())
                    .issueTime(new Date())
                    .expirationTime(new Date(new Date().getTime() + 1000 * 60 * 60 * 10)) // 10 giờ
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),
                    claimsSet);

            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(secretKey);

            if (!signedJWT.verify(verifier)) {
                return false;
            }

            String username = signedJWT.getJWTClaimsSet().getSubject();
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

            return username.equals(userDetails.getUsername()) && expiration.after(new Date());
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException("Error validating token", e);
        }
    }

    public String extractUsername(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new RuntimeException("Error extracting username from token", e);
        }
    }
}
