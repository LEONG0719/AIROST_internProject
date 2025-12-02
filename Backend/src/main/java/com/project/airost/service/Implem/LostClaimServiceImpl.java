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
    private final UserRepository userRepo; // Required to fix the user error
    private final AdminVerificationRepository adminVerificationRepo;
    private final AiService aiService;

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
        // ============================================================
        // 🚨 FIX: FETCH REAL USER FROM DB TO PREVENT HIBERNATE ERROR
        // ============================================================
        if (claim.getUser() == null || claim.getUser().getId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        User realUser = userRepo.findById(claim.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found in database"));

        // Attach the managed User entity to the claim
        claim.setUser(realUser);

        // Set default status if missing
        if (claim.getStatus() == null) {
            claim.setStatus(LostClaim.ClaimStatus.PENDING);
        }

        // Step: persist initial claim as PENDING
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

        if (bestImageScore >= 85) {
            saved.setStatus(LostClaim.ClaimStatus.APPROVED);
            saved.setBestSimilarityScore(bestImageScore);
            saved.setMatchedFoundItem(bestImageMatch);
            return lostClaimRepo.save(saved);
        } else if (bestImageScore >= 60) {
            saved.setStatus(LostClaim.ClaimStatus.LOW_CONFIDENCE);
            saved.setBestSimilarityScore(bestImageScore);
            saved.setMatchedFoundItem(bestImageMatch);
            // move to Layer 2 for further text check
        } else {
            // either no image or <60 => go to Layer 2
        }

        // ============================================================
        // 🔹 LAYER 2: TEXT MATCHING
        // ============================================================
        double bestTextScore = -1;
        FoundItem bestTextMatch = null;
        String concatLostText = joinNonNull(saved.getCategory(), saved.getBrand(), saved.getColor(), saved.getDescription());

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

        // Decision after Layer 2
        if (bestTextScore >= 80) {
            saved.setStatus(LostClaim.ClaimStatus.APPROVED);
            saved.setBestSimilarityScore(bestTextScore);
            saved.setMatchedFoundItem(bestTextMatch);
            return lostClaimRepo.save(saved);
        } else if (bestTextScore >= 60) {
            saved.setStatus(LostClaim.ClaimStatus.NEEDS_MANUAL_CHECK);
            saved.setBestSimilarityScore(bestTextScore);
            saved.setMatchedFoundItem(bestTextMatch);
            // notify admin for manual verification (hook for notification)
            return lostClaimRepo.save(saved);
        } else {
            // If we are here, Text failed (<60).
            // BUT check if Layer 1 (Image) had a "Low Confidence" match (e.g. 70%)
            // If Image was 70% but text is 20%, we should probably keep the Image result (Low Confidence/Manual Check)
            if (saved.getStatus() == LostClaim.ClaimStatus.LOW_CONFIDENCE) {
                // Keep the Layer 1 result
                return lostClaimRepo.save(saved);
            }

            // No match found
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

    // Helper method to join strings cleanly
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