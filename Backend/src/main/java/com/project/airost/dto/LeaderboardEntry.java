package com.project.airost.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaderboardEntry {
    private int rank;
    private String fullName;
    private Integer points;
    // can add 'imageUrl' or 'avatar' here later if have user profile pics
}