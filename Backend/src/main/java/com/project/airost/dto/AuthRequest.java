package com.project.airost.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private String fullName; // Optional, used only for Register
}
