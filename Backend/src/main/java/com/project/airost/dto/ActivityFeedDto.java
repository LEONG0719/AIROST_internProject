package com.project.airost.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;


@Data
@Builder
public class ActivityFeedDto {
    private String type;        // "LOST" or "FOUND" or "CLAIMED"
    private String title;       // e.g., "iPhone 13 Pro"
    private String description; // e.g., "Found at Library"
    private String timeAgo;     // e.g., "2 hours ago" (or just send raw LocalDateTime)
    private Instant timestamp;
}