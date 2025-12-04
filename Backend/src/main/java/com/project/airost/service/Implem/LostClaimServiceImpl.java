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

    // Points Constants
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

        // 2. Default Status
        if (claim.getStatus() == null) {
            claim.setStatus(LostClaim.ClaimStatus.PENDING);
        }

        // 3. Initial Save
        LostClaim saved = lostClaimRepo.save(claim);

        // ============================================================
        // 🔹 LAYER 1: AI IMAGE MATCHING
        // ============================================================
        double bestImageScore = -1;
        FoundItem bestImageMatch = null;

        // Only run Layer 1 if user uploaded an image
        if (saved.getImageUrl() != null && !saved.getImageUrl().isBlank()) {
            List<FoundItem> foundItems = foundItemRepo.findByClaimedFalse();
            for (FoundItem found : foundItems) {
                // Check if found item has a main image or gallery images
                String foundImgUrl = found.getImageUrl();

                // If main image exists, compare it
                if (foundImgUrl != null && !foundImgUrl.isBlank()) {
                    double score = aiService.compareImages(saved.getImageUrl(), foundImgUrl);
                    if (score > bestImageScore) {
                        bestImageScore = score;
                        bestImageMatch = found;
                    }
                }

                // Also check gallery images if any (optional based on your FoundItem structure)
                if (found.getImages() != null) {
                    for (FoundItemImage fi : found.getImages()) {
                        double score = aiService.compareImages(saved.getImageUrl(), fi.getImageUrl());
                        if (score > bestImageScore) {
                            bestImageScore = score;
                            bestImageMatch = found;
                        }
                    }
                }

                // Optimization: Stop if perfect match
                if (bestImageScore >= 100) break;
            }
        }

        // --- LAYER 1 DECISION LOGIC ---
        if (bestImageScore >= 70) {
            // ✅ High Confidence: Auto Approve
            saved.setStatus(LostClaim.ClaimStatus.APPROVED);
            saved.setBestSimilarityScore(bestImageScore);
            saved.setMatchedFoundItem(bestImageMatch);

            // Reward Points (Match)
            processMatchReward(bestImageMatch);

            return lostClaimRepo.save(saved);
        }
        else if (bestImageScore >= 50) {
            // ⚠️ Medium Confidence: Mark internally, but continue to Layer 2
            saved.setStatus(LostClaim.ClaimStatus.LOW_CONFIDENCE);
            saved.setBestSimilarityScore(bestImageScore);
            saved.setMatchedFoundItem(bestImageMatch);
        }
        // If < 60, we simply continue to Layer 2 (Status remains PENDING or whatever it was)

        // ============================================================
        // 🔹 LAYER 2: AI TEXT DESCRIPTION MATCHING
        // ============================================================
        double bestTextScore = -1;
        FoundItem bestTextMatch = null;

        // Construct detailed text string for Lost Item
        String concatLostText = joinNonNull(
                saved.getCategory(),
                saved.getBrand(),
                saved.getColor(),
                saved.getSpecialMarking(),
                saved.getLocation(),
                saved.getDescription()
        );

        if (concatLostText != null && !concatLostText.isBlank()) {
            List<FoundItem> foundItems = foundItemRepo.findByClaimedFalse();
            for (FoundItem found : foundItems) {
                // Construct detailed text string for Found Item
                String concatFoundText = joinNonNull(
                        found.getCategory(),
                        found.getBrand(),
                        found.getColor(),
                        found.getSpecialMarking(), // Include special marking in text compare
                        found.getDescription(),
                        found.getLocation()
                );

                double score = aiService.compareText(concatLostText, concatFoundText);
                if (score > bestTextScore) {
                    bestTextScore = score;
                    bestTextMatch = found;
                }
            }
        }

        // --- LAYER 2 DECISION LOGIC ---
        if (bestTextScore >= 70) {
            // ✅ High Confidence Text: Auto Approve
            saved.setStatus(LostClaim.ClaimStatus.APPROVED);
            saved.setBestSimilarityScore(bestTextScore);
            saved.setMatchedFoundItem(bestTextMatch);

            // Reward Points (Match)
            processMatchReward(bestTextMatch);

            return lostClaimRepo.save(saved);
        }
        else if (bestTextScore >= 50) {
            // ⚠️ Medium Confidence Text: Needs Manual Check (Layer 3)
            saved.setStatus(LostClaim.ClaimStatus.NEEDS_MANUAL_CHECK);
            saved.setBestSimilarityScore(bestTextScore);
            saved.setMatchedFoundItem(bestTextMatch);

            return lostClaimRepo.save(saved);
        }
        else {
            // ❌ Low Confidence Text (< 60%)

            // FALLBACK CHECK: Did Layer 1 have a "Low Confidence" match?
            // If Image was 70% but Text is 40%, we shouldn't say "No Match".
            // We should trust the image and ask Admin to verify.
            if (saved.getStatus() == LostClaim.ClaimStatus.LOW_CONFIDENCE) {
                saved.setStatus(LostClaim.ClaimStatus.NEEDS_MANUAL_CHECK);
                // Keep the Image Match items/score as they were better
                return lostClaimRepo.save(saved);
            }

            // If both Image AND Text failed
            saved.setStatus(LostClaim.ClaimStatus.NO_MATCH);
            saved.setBestSimilarityScore(Math.max(bestImageScore, bestTextScore)); // Record best attempt
            return lostClaimRepo.save(saved);
        }
    }

    @Override
    @Transactional
    public LostClaim submitProof(Long claimId, String studentIdNumber, String idImageUrl, String receiptUrl) {
        LostClaim claim = lostClaimRepo.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        // Update the proof fields
        claim.setProofStudentIdNumber(studentIdNumber);
        claim.setProofStudentIdImage(idImageUrl);
        claim.setProofReceiptImage(receiptUrl); // Optional, can be null

        // We don't change status here, Admin still needs to review
        return lostClaimRepo.save(claim);
    }

    // ==========================================
    // 🔹 LAYER 3: ADMIN VERIFICATION (Called by Controller)
    // ==========================================
    @Override
    @Transactional
    public LostClaim markApproved(Long claimId, Long adminId, String note) {
        LostClaim claim = lostClaimRepo.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        // 🚨 ENFORCE PROOF REQUIREMENTS 🚨
        // Admin cannot approve if user hasn't uploaded the mandatory proofs yet.
        if (claim.getProofStudentIdNumber() == null || claim.getProofStudentIdNumber().isBlank()) {
            throw new RuntimeException("Cannot approve: Student ID Number is missing.");
        }
        if (claim.getProofStudentIdImage() == null || claim.getProofStudentIdImage().isBlank()) {
            throw new RuntimeException("Cannot approve: Student ID Image is missing.");
        }
        // Note: Receipt is optional, so we don't throw exception if it's missing.

        // --- If checks pass, proceed to Approve ---
        claim.setStatus(LostClaim.ClaimStatus.APPROVED);

        // ✅ REWARD: If Admin approves, give Match points
        if (claim.getMatchedFoundItem() != null) {
            processMatchReward(claim.getMatchedFoundItem());
        }

        lostClaimRepo.save(claim);

        // Record the Admin's action
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
    // 🔹 CONFIRM RETURN (Owner got item back)
    // ==========================================
    @Override
    @Transactional
    public LostClaim confirmReturn(Long claimId, Long userId) {
        LostClaim claim = lostClaimRepo.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        if (!claim.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not the owner");
        }

        if (claim.getStatus() != LostClaim.ClaimStatus.APPROVED) {
            throw new RuntimeException("Claim not approved yet");
        }

        // ✅ REWARD: Give Return points (+50)
        FoundItem matchedItem = claim.getMatchedFoundItem();
        if (matchedItem != null && !matchedItem.isClaimed()) {

            // Mark item as officially gone from database list
            matchedItem.setClaimed(true);
            foundItemRepo.save(matchedItem);

            User finder = matchedItem.getUser();
            if (finder != null) {
                int currentPoints = finder.getPoints() == null ? 0 : finder.getPoints();
                finder.setPoints(currentPoints + POINTS_RETURN);
                userRepo.save(finder);
                System.out.println("🎉 Reward: Added " + POINTS_RETURN + " points to Finder (Return Confirmed)");
            }
        }
        return claim;
    }

    // --- HELPERS ---

    private void processMatchReward(FoundItem matchedItem) {
        // Awards points for a successful MATCH (+25)
        // Does NOT mark as claimed yet (User must confirm receipt for that)
        if (matchedItem != null) {
            User finder = matchedItem.getUser();
            if (finder != null) {
                // Optional: Check if we already gave match points to prevent duplicates
                // For now, simpler logic: just add.
                int currentPoints = finder.getPoints() == null ? 0 : finder.getPoints();
                finder.setPoints(currentPoints + POINTS_MATCH);
                userRepo.save(finder);
                System.out.println("🎉 Reward: Added " + POINTS_MATCH + " points to Finder (Match Approved)");
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

    @Override
    public Optional<LostClaim> findById(Long id) {
        return lostClaimRepo.findById(id);
    }
}