package com.project.airost.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    public enum Role { USER, ADMIN }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String passwordHash; // store bcrypt

    @Column(nullable=false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role = Role.USER;

    private Instant createdAt = Instant.now();

    //for verification
    @Column(name = "is_enabled")
    private boolean enabled = false; // Default is FALSE (cannot login)

    @Column(name = "verification_code", length = 64)
    private String verificationCode; // Stores the random token
}
    // getters / setters / constructors
