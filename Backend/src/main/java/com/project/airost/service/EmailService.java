package com.project.airost.service;

import com.project.airost.domain.User;

public interface EmailService {
    void sendVerificationEmail(User user, String siteUrl);
}