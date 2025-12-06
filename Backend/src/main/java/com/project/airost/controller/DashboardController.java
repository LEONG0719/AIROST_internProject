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

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"}, allowedHeaders = "*", methods = {RequestMethod.GET})
@RequiredArgsConstructor
public class DashboardController {

    private final FoundItemRepository foundItemRepo;
    private final LostClaimRepository lostClaimRepo;
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    @GetMapping("/recent-activity")
    public ResponseEntity<List<ActivityFeedDto>> getRecentActivity(@RequestHeader("Authorization") String token) {

        // 1. Identify User
        String jwt = token.substring(7);
        String email = jwtUtil.extractUsername(jwt);
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = user.getId();

        List<ActivityFeedDto> activities = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy").withZone(ZoneId.systemDefault());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a").withZone(ZoneId.systemDefault());

        // 2. FOUND ITEMS (Your Reports)
        List<FoundItem> foundItems = foundItemRepo.findByUserId(userId);
        for (FoundItem item : foundItems) {
            activities.add(ActivityFeedDto.builder()
                    .type("FOUND")
                    .modalType(null)  // ✅ Not clickable
                    .title("You found: " + item.getTitle())
                    .description("Reported on " + dateFormatter.format(item.getFoundAt()))
                    .timeAgo(item.getFoundAt().toString())
                    .timestamp(item.getFoundAt())
                    .category(item.getCategory())
                    .color(item.getColor())
                    .imageUrl(item.getImageUrl())
                    .build());
        }

        // 3. LOST CLAIMS (Your Requests)
        List<LostClaim> lostClaims = lostClaimRepo.findByUserId(userId);
        for (LostClaim claim : lostClaims) {

            // --- DEFAULT VALUES ---
            String type = "LOST";
            String modalType = null;
            String title = claim.getCategory() + " (" + claim.getBrand() + ")";
            String description = "Status: " + claim.getStatus();
            String returnLoc = null;
            String returnDate = null;
            String returnTime = null;

            // --- SCENARIO-BASED LOGIC ---

            // ✅ Scenario 1: APPROVED → Item Matched, Ready to Claim
            if (claim.getStatus() == LostClaim.ClaimStatus.APPROVED) {
                type = "LOST_MATCHED";
                modalType = "CLAIM";  // ← Opens ItemClaimModal
                title = "🎉 Your " + claim.getCategory() + " Found!";
                description = "Match confirmed! Click to review details and claim your item.";

                // Get return details from Matched Item
                if (claim.getMatchedFoundItem() != null) {
                    FoundItem matched = claim.getMatchedFoundItem();
                    returnLoc = "Central Claim Office"; // Or "UTM Lost & Found Office (FC N28)"
                    if (matched.getDropOffTime() != null) {
                        returnDate = dateFormatter.format(matched.getDropOffTime());
                        returnTime = timeFormatter.format(matched.getDropOffTime());
                    }
                }
            }

            // ⏳ Scenario 2: NEEDS_MANUAL_CHECK → Already Submitted, Waiting for Admin
            else if (claim.getStatus() == LostClaim.ClaimStatus.NEEDS_MANUAL_CHECK) {
                type = "PENDING_VERIFICATION";
                modalType = null;  // ← NOT clickable (already submitted proof)
                title = "📄 " + claim.getCategory() + " - Pending Verification";
                description = "Verification documents submitted. Waiting for admin approval.";
            }

            // ❌ Scenario 3: REJECTED → Admin Rejected
            else if (claim.getStatus() == LostClaim.ClaimStatus.REJECTED) {
                type = "REJECTED";
                modalType = null;  // ← Not clickable
                title = "❌ " + claim.getCategory() + " - Verification Failed";
                // Note: Ensure your LostClaim entity has 'getAdminNote()' or remove the specific note part
                description = "Admin rejected your claim. Please contact support.";
            }

            // ✅ Scenario 4: CLAIMED → Already Claimed
            else if (claim.getStatus() == LostClaim.ClaimStatus.CLAIMED) {
                type = "CLAIMED";
                modalType = null;  // ← Not clickable
                title = "✅ " + claim.getCategory() + " Claimed";
                description = "You have successfully claimed this item!";
            }

            // 📝 Scenario 5: PENDING → Just Reported, Waiting for AI Match
            else if (claim.getStatus() == LostClaim.ClaimStatus.PENDING) {
                type = "LOST";
                modalType = null;  // ← Not clickable
                title = claim.getCategory() + " - " + claim.getBrand();
                description = "Your lost item report is being processed by our AI matching system.";
            }

            // --- BUILD DTO ---
            activities.add(ActivityFeedDto.builder()
                    .type(type)
                    .modalType(modalType)
                    .claimId(claim.getId())
                    .title(title)
                    .description(description)
                    .timestamp(claim.getCreatedAt())
                    .timeAgo(claim.getCreatedAt().toString())

                    // Frontend Data
                    .category(claim.getCategory())
                    .color(claim.getColor())
                    .imageUrl(claim.getImageUrl()) // Show the image User uploaded
                    .returnLocation(returnLoc)
                    .returnDate(returnDate)
                    .returnTime(returnTime)
                    .build());
        }

        // 4. Sort by Newest First & Limit to 10
        List<ActivityFeedDto> sortedActivities = activities.stream()
                .sorted(Comparator.comparing(ActivityFeedDto::getTimestamp).reversed())
                .limit(10)
                .collect(Collectors.toList());

        return ResponseEntity.ok(sortedActivities);
    }
}

