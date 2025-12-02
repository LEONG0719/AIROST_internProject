package com.project.airost.service;

import com.project.airost.domain.User;

public interface AuthService {
    // Registers a user and sends the verification email
    User registerUser(User user);

    // Checks username/password and ensures account is verified
    User login(String email, String password);

    // Verifies the code from the email link
    boolean verify(String verificationCode);
}