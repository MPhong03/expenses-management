package com.mphong.EMWebAPI.Controllers;

import com.mphong.EMWebAPI.Models.EmailDetail;
import com.mphong.EMWebAPI.Models.OTPRequest;
import com.mphong.EMWebAPI.Models.OTPValidiation;
import com.mphong.EMWebAPI.Models.User;
import com.mphong.EMWebAPI.Services.EmailServiceImpl;
import com.mphong.EMWebAPI.Services.UserService;
import com.mphong.EMWebAPI.Utils.JwtUtil;
import com.mphong.EMWebAPI.Utils.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://192.168.1.3:8080")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final EmailServiceImpl emailService;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil, EmailServiceImpl emailService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService =userDetailsService;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        try {
            userService.signUp(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody User user, @RequestParam(defaultValue = "false") boolean isRemember) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails, isRemember);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/getOTP")
    public ResponseEntity<String> sendMailToGetOTP(@RequestBody OTPRequest request) {
        User user = userService.findByEmail(request.getEmail());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Generate OTP
        String otp = emailService.generateOTP(6);
        Date otpExpiryTime = new Date(System.currentTimeMillis() + 2 * 60 * 1000);

        user.setOtp(otp);
        user.setOtpExpiryTime(otpExpiryTime);

        userService.saveUser(user);

        // Build mail
        String subject = "RESET PASSWORD OTP";
        String msg = "Hi, " + user.getEmail() + "\n" +
                "This is OTP to reset password, please don't share this code with anyone else.\n" +
                "Here is your OTP: " + otp + "\n";

        EmailDetail details = new EmailDetail(user.getEmail(), subject, msg);

        String status = emailService.sendMail(details);

        return ResponseEntity.ok(status);
    }

    @PostMapping("/checkOTP")
    public ResponseEntity<String> checkOTP(@RequestBody OTPValidiation request) {
        String email = request.getEmail().trim().toLowerCase();
        String otp = request.getOtp();

        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (user.getOtp() == null || !user.getOtp().equals(otp)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }

        if (user.getOtpExpiryTime().before(new Date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP has expired");
        }

        user.setCanChangePassword(true);
        userService.saveUser(user);

        return ResponseEntity.ok("OTP validated successfully");
    }
}
