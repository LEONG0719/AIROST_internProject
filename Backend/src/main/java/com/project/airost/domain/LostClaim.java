package com.project.airost.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lost_claims")
public class LostClaim {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private User user;

    @Column(length=2000)
    private String imageUrl; // optional lost image

    @Column(length=2000)
    private String description; // user text description

    private String specialMarking;

    private String category;
    private String color;
    private String brand;
    private String remarks;
    private String location;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status = ClaimStatus.PENDING;

    private Double bestSimilarityScore; // best score found (0-100)

    @ManyToOne
    private FoundItem matchedFoundItem; // best matched item if any

    @ElementCollection
    private List<String> extraProofUrls; // receipts etc

    // === 🆕 NEW FIELDS FOR LAYER 3 PROOF ===
    // We store the text ID specifically for this claim record
    private String proofStudentIdNumber;

    // URL to the image of the Student ID Card (Mandatory for approval)
    private String proofStudentIdImage;

    // URL to the receipt image (Optional)
    private String proofReceiptImage;

    private Instant createdAt = Instant.now();

    // getters/setters

    public enum ClaimStatus {
        PENDING, LOW_CONFIDENCE, NEEDS_MANUAL_CHECK, NO_MATCH, APPROVED, REJECTED, CLAIMED
    }
}