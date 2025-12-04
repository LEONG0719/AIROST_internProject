package com.project.airost.controller;

import com.project.airost.domain.User;
import com.project.airost.dto.AuthRequest;
import com.project.airost.dto.AuthResponse;
import com.project.airost.repository.UserRepository;
import com.project.airost.security.JwtUtil;
import com.project.airost.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5177", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    // NOTE: You don't need UserRepository here anymore because logic moved to Service
    // but if you used it for quick checks, that's fine. Cleaner to keep it in Service.

    // 1. REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        try {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPasswordHash(request.getPassword());
            user.setFullName(request.getFullName());
            user.setRole(User.Role.USER); // Default role

            authService.registerUser(user);
            return ResponseEntity.ok("Registration successful! Please check your email to verify.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. VERIFY EMAIL
    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("code") String code) {
        if (authService.verify(code)) {
            return ResponseEntity.ok("Verify success! You can now login.");
        } else {
            return ResponseEntity.badRequest().body("Verify failed. Code invalid or already verified.");
        }
    }

    // 3. LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // The service now handles password check AND enabled check
            User user = authService.login(request.getEmail(), request.getPassword());

            // Generate Token
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

            return ResponseEntity.ok(new AuthResponse(token, user.getRole().name(), user.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}