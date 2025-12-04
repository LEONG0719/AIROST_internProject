package com.project.airost.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private Long id;
    private String fullName;
    private String studentId;
    private String email;
    private Integer points;

    // Stats for the cards
    private long itemsFoundCount;
    private long itemsLostCount;
    private long itemsMatchedCount; // Successful recoveries

    // Leaderboard info
    private long globalRank;
    private long totalUsers;

    private String phoneNumber;
}