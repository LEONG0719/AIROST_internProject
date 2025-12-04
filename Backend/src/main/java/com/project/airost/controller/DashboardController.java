package com.project.airost.controller;

import com.project.airost.domain.FoundItem;
import com.project.airost.domain.LostClaim;
import com.project.airost.dto.ActivityFeedDto;
import com.project.airost.repository.FoundItemRepository;
import com.project.airost.repository.LostClaimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5174", allowedHeaders = "*", methods = {RequestMethod.GET})
@RequiredArgsConstructor
public class DashboardController {

    private final FoundItemRepository foundItemRepo;
    private final LostClaimRepository lostClaimRepo;

    @GetMapping("/recent-activity")
    public List<ActivityFeedDto> getRecentActivity() {
        List<ActivityFeedDto> activities = new ArrayList<>();

        // 1. Get recent Found Items (Limit 5)
        // Note: You might want to add pagination or "Top 5" query in repository later
        List<FoundItem> foundItems = foundItemRepo.findAll();
        for (FoundItem item : foundItems) {
            activities.add(ActivityFeedDto.builder()
                    .type("FOUND")
                    .title(item.getTitle()) // Ensure FoundItem has getTitle()
                    .description("Found at " + item.getFoundAt()) // Customize this string
                    .timestamp(item.getFoundAt())
                    .build());
        }

        // 2. Get recent Lost Claims (Limit 5)
        List<LostClaim> lostClaims = lostClaimRepo.findAll();
        for (LostClaim claim : lostClaims) {
            activities.add(ActivityFeedDto.builder()
                    .type("LOST")
                    .title(claim.getCategory() + " - " + claim.getBrand())
                    .description("Lost: " + claim.getDescription())
                    .timestamp(claim.getCreatedAt()) // Ensure LostClaim has getCreatedAt()
                    .build());
        }

        // 3. Sort by newest first and pick top 5-10
        return activities.stream()
                .sorted(Comparator.comparing(ActivityFeedDto::getTimestamp).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}