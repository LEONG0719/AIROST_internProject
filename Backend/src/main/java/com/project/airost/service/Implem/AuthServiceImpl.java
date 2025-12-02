package com.project.airost.service.Implem;

import com.project.airost.domain.User;
import com.project.airost.repository.UserRepository;
import com.project.airost.service.AuthService;
import com.project.airost.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final EmailService emailService;

    @Override
    public User registerUser(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        // 1. Generate Random Verification Code
        String randomCode = UUID.randomUUID().toString();
        user.setVerificationCode(randomCode);
        user.setEnabled(false); // User cannot login until verified

        // 2. Save User
        User savedUser = userRepo.save(user);

        // 3. Send Email
        // Note: Change "http://localhost:8085" to your real frontend URL in production
        emailService.sendVerificationEmail(savedUser, "http://localhost:8085");

        return savedUser;
    }

    @Override
    public User login(String email, String password) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1. Check if verified
        if (!user.isEnabled()) {
            throw new RuntimeException("Account is not verified. Please check your email.");
        }

        // 2. Check Password (In real app, use passwordEncoder.matches())
        if (!user.getPasswordHash().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = userRepo.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null); // Clear the code
            user.setEnabled(true);          // Enable the account
            userRepo.save(user);
            return true;
        }
    }
}