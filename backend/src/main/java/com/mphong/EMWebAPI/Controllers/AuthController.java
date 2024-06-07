package com.mphong.EMWebAPI.Controllers;

import com.mphong.EMWebAPI.Models.User;
import com.mphong.EMWebAPI.Services.UserService;
import com.mphong.EMWebAPI.Utils.JwtUtil;
import com.mphong.EMWebAPI.Utils.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService =userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        userService.signUp(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }
}
