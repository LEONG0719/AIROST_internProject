package com.project.airost.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunityStatsResponse {
    private long itemsReturned;   // "89" in your design
    private long activeHeroes;    // "234" (Total Users)
    private double successRate;   // "95%" (Claimed / Total Found)
    private long totalPoints;     // "1847" (Sum of all user points)
}