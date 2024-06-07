package com.mphong.EMWebAPI.Controllers;

import com.mphong.EMWebAPI.Models.User;
import com.mphong.EMWebAPI.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "hello world";
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        userService.signUp(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        User result = userService.signIn(user.getUsername(), user.getPassword());
        return ResponseEntity.ok("User logged in successfully: " + result.getUsername());
    }
}
