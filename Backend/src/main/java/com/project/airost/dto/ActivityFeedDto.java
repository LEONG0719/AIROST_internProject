package com.project.airost.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityFeedDto {
    private String type;        // "LOST", "FOUND", "LOST_MATCHED"
    private String title;
    private String description;
    private String timeAgo;     // Formatted string (e.g., "2 hours ago")
    private Instant timestamp;  // For sorting

    // 🆕 NEW FIELDS FOR MODALS
    private String modalType;      // "CLAIM", "VERIFY", or null
    private Long claimId;          // ID needed for API calls
    private String category;       // To show item icon/details
    private String color;          // To show item details
    private String imageUrl;       // To show preview image

    // 🆕 RETURN DETAILS (For Claim Modal)
    private String returnLocation; // e.g., "Student Union Office"
    private String returnDate;     // e.g., "2023-10-30"
    private String returnTime;     // e.g., "14:00 PM"
}