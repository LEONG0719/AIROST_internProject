package com.project.airost.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin_verifications")
public class AdminVerification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private LostClaim claim;

    @ManyToOne
    private User admin; // admin who handled

    private String notes;

    private Instant reviewedAt = Instant.now();

    @Enumerated(EnumType.STRING)
    private VerificationResult result;

    public enum VerificationResult { APPROVED, REJECTED }
    // getters/setters
}

