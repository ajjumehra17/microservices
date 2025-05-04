package com.service.user.controller;


import com.service.user.config.JwtUtil;

import com.service.user.entity.User;
import com.service.user.model.LoginRequest;
import com.service.user.model.LoginResponse;
import com.service.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Login attempt for username: {}", loginRequest.getUsername());

        Optional<User> userOptional = userService.findByUsername(loginRequest.getUsername());

        if (userOptional.isEmpty()) {
            logger.warn("Login failed: user not found - {}", loginRequest.getUsername());
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        User user = userOptional.get();
        logger.info("Input Password: {}", loginRequest.getPassword());
        boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        logger.info("User found: {}, Password matches: {}", user.getUsername(), passwordMatches);

        if (passwordMatches) {
            String token = jwtUtil.generateToken(user.getUsername(), user.getId());
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUserId(user.getId());
            logger.info("Login successful for user: {}", user.getUsername());
            return ResponseEntity.ok(response);
        } else {
            logger.warn("Login failed: incorrect password for user: {}", user.getUsername());
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        try {
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                Long userId = jwtUtil.extractUserId(token);
                return ResponseEntity.ok("Valid token for user: " + username + ", id: " + userId);
            } else {
                return ResponseEntity.status(401).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token: " + e.getMessage());
        }
    }
}