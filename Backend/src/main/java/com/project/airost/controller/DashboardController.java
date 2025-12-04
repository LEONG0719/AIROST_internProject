package com.project.airost.controller;

import com.project.airost.domain.FoundItem;
import com.project.airost.domain.LostClaim;
import com.project.airost.domain.User;
import com.project.airost.dto.ActivityFeedDto;
import com.project.airost.repository.FoundItemRepository;
import com.project.airost.repository.LostClaimRepository;
import com.project.airost.repository.UserRepository;
import com.project.airost.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", methods = {RequestMethod.GET})
@RequiredArgsConstructor
public class DashboardController {

    private final FoundItemRepository foundItemRepo;
    private final LostClaimRepository lostClaimRepo;
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil; // Need this to read the token

    @GetMapping("/recent-activity")
    public ResponseEntity<List<ActivityFeedDto>> getRecentActivity(@RequestHeader("Authorization") String token) {

        // 1. Identify the User from the Token
        String jwt = token.substring(7);
        String email = jwtUtil.extractUsername(jwt);
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = user.getId();

        List<ActivityFeedDto> activities = new ArrayList<>();

        // 2. Get Found Items reported by THIS user
        List<FoundItem> foundItems = foundItemRepo.findByUserId(userId);
        for (FoundItem item : foundItems) {
            activities.add(ActivityFeedDto.builder()
                    .type("FOUND")
                    .title(item.getTitle())
                    .description("You reported finding this item.")
                    .timeAgo(item.getFoundAt().toString()) // Frontend handles formatting
                    .timestamp(item.getFoundAt())
                    .build());
        }

        // 3. Get Lost Claims submitted by THIS user
        List<LostClaim> lostClaims = lostClaimRepo.findByUserId(userId);
        for (LostClaim claim : lostClaims) {
            String statusMsg = "Status: " + claim.getStatus();
            if (claim.getStatus().toString().equals("APPROVED")) {
                statusMsg = "🎉 Match Found! Item returned.";
            }

            activities.add(ActivityFeedDto.builder()
                    .type("LOST")
                    .title(claim.getCategory() + " - " + claim.getBrand())
                    .description("You reported this lost item. " + statusMsg)
                    .timeAgo(claim.getCreatedAt().toString())
                    .timestamp(claim.getCreatedAt())
                    .build());
        }

        // 4. Sort and Limit
        List<ActivityFeedDto> sortedActivities = activities.stream()
                .sorted(Comparator.comparing(ActivityFeedDto::getTimestamp).reversed())
                .limit(10)
                .collect(Collectors.toList());

        return ResponseEntity.ok(sortedActivities);
    }
}