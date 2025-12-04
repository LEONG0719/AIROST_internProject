package com.project.airost.service.Implem;

import com.project.airost.domain.*;
import com.project.airost.repository.*;
import com.project.airost.service.AiService;
import com.project.airost.service.LostClaimService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LostClaimServiceImpl implements LostClaimService {

    private final LostClaimRepository lostClaimRepo;
    private final FoundItemRepository foundItemRepo;
    private final UserRepository userRepo;
    private final AdminVerificationRepository adminVerificationRepo;
    private final AiService aiService;

    // Fixed Points amount
    private static final int POINTS_REWARD = 10;
    private static final int POINTS_MATCH = 25;
    private static final int POINTS_RETURN = 50;

    public LostClaimServiceImpl(LostClaimRepository lostClaimRepo,
                                FoundItemRepository foundItemRepo,
                                UserRepository userRepo,
                                AdminVerificationRepository adminVerificationRepo,
                                AiService aiService) {
        this.lostClaimRepo = lostClaimRepo;
        this.foundItemRepo = foundItemRepo;
        this.userRepo = userRepo;
        this.adminVerificationRepo = adminVerificationRepo;
        this.aiService = aiService;
    }

    @Override
    @Transactional
    public LostClaim submitClaim(LostClaim claim) {
        // 1. Fetch Real User
        if (claim.getUser() == null || claim.getUser().getId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        User realUser = userRepo.findById(claim.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found in database"));
        claim.setUser(realUser);

        if (claim.getStatus() == null) {
            claim.setStatus(LostClaim.ClaimStatus.PENDING);
        }

        // 2. Initial Save
        LostClaim saved = lostClaimRepo.save(claim);

        // ============================================================
        // 🔹 LAYER 1: AI IMAGE MATCHING
        // ============================================================
        double bestImageScore = -1;
        FoundItem bestImageMatch = null;

        if (saved.getImageUrl() != null && !saved.getImageUrl().isBlank()) {
            List<FoundItem> foundItems = foundItemRepo.findByClaimedFalse();
            for (FoundItem found : foundItems) {
                if (found.getImages() == null) continue;
                for (FoundItemImage fi : found.getImages()) {
                    double score = aiService.compareImages(saved.getImageUrl(), fi.getImageUrl());
                    if (score > bestImageScore) {
                        bestImageScore = score;
                        bestImageMatch = found;
                    }
                    if (bestImageScore >= 100) break;
                }
            }
        }

        // --- IMAGE DECISION ---
        if (bestImageScore >= 85) {
            saved.setStatus(LostClaim.ClaimStatus.APPROVED);
            saved.setBestSimilarityScore(bestImageScore);
            saved.setMatchedFoundItem(bestImageMatch);

            // ✅ CHANGED: Use helper to Reward Points + Mark Claimed
            rewardFinder(bestImageMatch);

            return lostClaimRepo.save(saved);
        } else if (bestImageScore >= 60) {
            saved.setStatus(LostClaim.ClaimStatus.LOW_CONFIDENCE);
            saved.setBestSimilarityScore(bestImageScore);
            saved.setMatchedFoundItem(bestImageMatch);
        }

        // ============================================================
        // 🔹 LAYER 2: TEXT MATCHING
        // ============================================================
        double bestTextScore = -1;
        FoundItem bestTextMatch = null;
        String concatLostText = joinNonNull(saved.getCategory(), saved.getBrand(), saved.getColor(), saved.getSpecialMarking(), saved.getDescription());

        if (concatLostText != null && !concatLostText.isBlank()) {
            List<FoundItem> foundItems = foundItemRepo.findByClaimedFalse();
            for (FoundItem found : foundItems) {
                String concatFoundText = joinNonNull(found.getCategory(), found.getBrand(), found.getColor(), found.getDescription());
                double score = aiService.compareText(concatLostText, concatFoundText);
                if (score > bestTextScore) {
                    bestTextScore = score;
                    bestTextMatch = found;
                }
            }
        }

        // --- TEXT DECISION ---
        if (bestTextScore >= 80) {
            saved.setStatus(LostClaim.ClaimStatus.APPROVED);
            saved.setBestSimilarityScore(bestTextScore);
            saved.setMatchedFoundItem(bestTextMatch);

            //  Use helper to Reward Points + Mark Claimed
            rewardFinder(bestTextMatch);

            return lostClaimRepo.save(saved);
        } else if (bestTextScore >= 60) {
            saved.setStatus(LostClaim.ClaimStatus.NEEDS_MANUAL_CHECK);
            saved.setBestSimilarityScore(bestTextScore);
            saved.setMatchedFoundItem(bestTextMatch);
            return lostClaimRepo.save(saved);
        } else {
            // Check if Layer 1 (Image) had a "Low Confidence" match, keep it if Text failed
            if (saved.getStatus() == LostClaim.ClaimStatus.LOW_CONFIDENCE) {
                return lostClaimRepo.save(saved);
            }

            saved.setStatus(LostClaim.ClaimStatus.NO_MATCH);
            saved.setBestSimilarityScore(bestTextScore >= 0 ? bestTextScore : null);
            return lostClaimRepo.save(saved);
        }
    }

    @Override
    public Optional<LostClaim> findById(Long id) {
        return lostClaimRepo.findById(id);
    }

    @Override
    @Transactional
    public LostClaim markApproved(Long claimId, Long adminId, String note) {
        LostClaim claim = lostClaimRepo.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        claim.setStatus(LostClaim.ClaimStatus.APPROVED);

        // Use helper to Reward Points + Mark Claimed
        if (claim.getMatchedFoundItem() != null) {
            rewardFinder(claim.getMatchedFoundItem());
        }

        lostClaimRepo.save(claim);

        AdminVerification av = new AdminVerification();
        av.setClaim(claim);
        av.setAdmin(userRepo.findById(adminId).orElse(null));
        av.setResult(AdminVerification.VerificationResult.APPROVED);
        av.setNotes(note);
        adminVerificationRepo.save(av);
        return claim;
    }

    @Override
    @Transactional
    public LostClaim markRejected(Long claimId, Long adminId, String note) {
        LostClaim claim = lostClaimRepo.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        claim.setStatus(LostClaim.ClaimStatus.REJECTED);
        lostClaimRepo.save(claim);

        AdminVerification av = new AdminVerification();
        av.setClaim(claim);
        av.setAdmin(userRepo.findById(adminId).orElse(null));
        av.setResult(AdminVerification.VerificationResult.REJECTED);
        av.setNotes(note);
        adminVerificationRepo.save(av);
        return claim;
    }

    // ==========================================
    // 🔹 NEW HELPER METHOD TO ADD POINTS
    // ==========================================
    private void rewardFinder(FoundItem matchedItem) {
        // Only give points if the item wasn't already marked as claimed (prevents double points)
        if (matchedItem != null && !matchedItem.isClaimed()) {

            // 1. Mark item as claimed (Item Returned)
            matchedItem.setClaimed(true);
            foundItemRepo.save(matchedItem);

            // 2. Add points to the Finder
            User finder = matchedItem.getUser();
            if (finder != null) {
                int currentPoints = finder.getPoints() == null ? 0 : finder.getPoints();

                // Logic: Successful Match (+25) + Item Returned (+50)
                int totalReward = POINTS_MATCH + POINTS_RETURN;

                finder.setPoints(currentPoints + totalReward);
                userRepo.save(finder);

                System.out.println("🎉 Added " + totalReward + " points to Finder ID: " + finder.getId());
            }
        }
    }
    private String joinNonNull(String... parts) {
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (part != null && !part.isBlank()) {
                sb.append(part).append(" ");
            }
        }
        return sb.toString().trim();
    }
}