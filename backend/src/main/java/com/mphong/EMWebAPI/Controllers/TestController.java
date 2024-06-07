package com.mphong.EMWebAPI.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    // MUST SIGN IN
    @GetMapping("/hello")
    public ResponseEntity<String> testHello() {
        return ResponseEntity.ok("Hello User");
    }
}
