package com.project.airost.dto;

import lombok.Data;

@Data
public class LostClaimRequest {
    // This ID tells us which user is submitting the claim
    private Long userId;

    // Item details
    private String imageUrl;
    private String description;
    private String category;
    private String color;
    private String brand;
    private String remarks;
    private String specialMarking;

    // Optional: for Layer 3 verification later
    private String extraProofUrls;
}