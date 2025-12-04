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

    //  Reward Constants
    private static final int POINTS_MATCH = 25;
    private static final int POINTS_RETURN = 50;

    public LostClaimServiceImpl(LostClaimRepository lostClaimRepo, FoundItemRepository foundItemRepo, UserRepository userRepo, AdminVerificationRepository adminVerificationRepo, AiService aiService) {
        this.lostClaimRepo = lostClaimRepo;
        this.foundItemRepo = foundItemRepo;
        this.userRepo = userRepo;
        this.adminVerificationRepo = adminVerificationRepo;
        this.aiService = aiService;
    }

    @Override
    @Transactional
    public LostClaim submitClaim(LostClaim claim) {
        // Validation
        if (claim.getUser() == null || claim.getUser().getId() == null) throw new IllegalArgumentException("User ID required");
        User realUser = userRepo.findById(claim.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found"));
        claim.setUser(realUser);
        claim.setStatus(LostClaim.ClaimStatus.PENDING);

        LostClaim saved = lostClaimRepo.save(claim);

        // ============================================================
        // 🔹 LAYER 1: AI IMAGE MATCHING
        // ============================================================
        double bestImageScore = 0;
        FoundItem bestImageMatch = null;

        if (saved.getImageUrl() != null && !saved.getImageUrl().isBlank()) {
            List<FoundItem> foundItems = foundItemRepo.findByClaimedFalse();
            for (FoundItem found : foundItems) {
                double score = aiService.compareImages(saved.getImageUrl(), found.getImageUrl());
                if (score > bestImageScore) {
                    bestImageScore = score;
                    bestImageMatch = found;
                }
                if (bestImageScore >= 95) break; // Optimization
            }
        }

        // --- LAYER 1 DECISION ---
        if (bestImageScore >= 80) { // High confidence image = Auto Approve
            return autoApprove(saved, bestImageMatch, bestImageScore);
        }

        // ============================================================
        // 🔹 LAYER 2: AI TEXT MATCHING
        // ============================================================
        double bestTextScore = 0;
        FoundItem bestTextMatch = null;

        // Build search strings
        String lostText = joinNonNull(saved.getCategory(), saved.getBrand(), saved.getDescription());

        List<FoundItem> foundItems = foundItemRepo.findByClaimedFalse();
        for (FoundItem found : foundItems) {
            String foundText = joinNonNull(found.getCategory(), found.getBrand(), found.getDescription());
            double score = aiService.compareText(lostText, foundText);
            if (score > bestTextScore) {
                bestTextScore = score;
                bestTextMatch = found;
            }
        }

        // --- LAYER 2 DECISION ---
        if (bestTextScore >= 80) {
            // High confidence text = Auto Approve
            return autoApprove(saved, bestTextMatch, bestTextScore);
        }
        else if (bestTextScore >= 50 || bestImageScore >= 50) {
            // ⚠️ Medium Confidence = Manual Check Required
            // We prioritize the item with the higher score for the match
            FoundItem bestMatch = (bestImageScore > bestTextScore) ? bestImageMatch : bestTextMatch;
            double bestScore = Math.max(bestImageScore, bestTextScore);

            saved.setStatus(LostClaim.ClaimStatus.NEEDS_MANUAL_CHECK);
            saved.setMatchedFoundItem(bestMatch);
            saved.setBestSimilarityScore(bestScore);
            return lostClaimRepo.save(saved);
        }

        // No Match Found
        saved.setStatus(LostClaim.ClaimStatus.NO_MATCH);
        return lostClaimRepo.save(saved);
    }

    // --- Helper: Auto Approve Logic ---
    private LostClaim autoApprove(LostClaim claim, FoundItem match, double score) {
        claim.setStatus(LostClaim.ClaimStatus.APPROVED);
        claim.setMatchedFoundItem(match);
        claim.setBestSimilarityScore(score);

        // 🎁 REWARD STAGE 1: Match Found (+25 pts)
        giveReward(match.getUser(), POINTS_MATCH);

        return lostClaimRepo.save(claim);
    }

    // ============================================================
    // 🔹 LAYER 3: MANUAL VERIFICATION (User Uploads Proof)
    // ============================================================
    @Override
    @Transactional
    public LostClaim submitProof(Long claimId, String studentIdNumber, String idImageUrl, String receiptUrl) {
        LostClaim claim = lostClaimRepo.findById(claimId).orElseThrow(() -> new RuntimeException("Claim not found"));

        if (claim.getStatus() != LostClaim.ClaimStatus.NEEDS_MANUAL_CHECK &&
                claim.getStatus() != LostClaim.ClaimStatus.REJECTED) { // Allow retry if rejected? Optional.
            throw new RuntimeException("This claim does not require manual verification.");
        }

        claim.setProofStudentIdNumber(studentIdNumber);
        claim.setProofStudentIdImage(idImageUrl);
        claim.setProofReceiptImage(receiptUrl);
        claim.setStatus(LostClaim.ClaimStatus.NEEDS_MANUAL_CHECK);

        return lostClaimRepo.save(claim);
    }

    // ============================================================
    // 🔹 ADMIN APPROVAL (Gatekeeper)
    // ============================================================
    @Override
    @Transactional
    public LostClaim markApproved(Long claimId, Long adminId, String note) {
        LostClaim claim = lostClaimRepo.findById(claimId).orElseThrow(() -> new RuntimeException("Claim not found"));

        // 🛡Enforce Proof Requirements
        if (claim.getProofStudentIdNumber() == null || claim.getProofStudentIdImage() == null) {
            throw new RuntimeException("Cannot approve: Missing Student ID proofs.");
        }

        claim.setStatus(LostClaim.ClaimStatus.APPROVED);

        //  REWARD STAGE 1 (Delayed): Match Approved (+25 pts)
        // We check if points were already given to prevent double counting
        // (In this flow, they hit MANUAL_CHECK first, so they haven't received points yet)
        if (claim.getMatchedFoundItem() != null) {
            giveReward(claim.getMatchedFoundItem().getUser(), POINTS_MATCH);
        }

        saveAdminLog(claim, adminId, AdminVerification.VerificationResult.APPROVED, note);
        return lostClaimRepo.save(claim);
    }

    @Override
    @Transactional
    public LostClaim markRejected(Long claimId, Long adminId, String note) {
        LostClaim claim = lostClaimRepo.findById(claimId).orElseThrow();
        claim.setStatus(LostClaim.ClaimStatus.REJECTED);
        saveAdminLog(claim, adminId, AdminVerification.VerificationResult.REJECTED, note);
        return lostClaimRepo.save(claim);
    }

    // ============================================================
    // 🔹 FULFILLMENT: CONFIRM RETURN
    // ============================================================
    @Override
    @Transactional
    public LostClaim confirmReturn(Long claimId, Long userId) {
        LostClaim claim = lostClaimRepo.findById(claimId).orElseThrow(() -> new RuntimeException("Claim not found"));

        if (!claim.getUser().getId().equals(userId)) throw new RuntimeException("Not authorized");
        if (claim.getStatus() != LostClaim.ClaimStatus.APPROVED) throw new RuntimeException("Claim not approved");

        // Mark item as claimed
        FoundItem item = claim.getMatchedFoundItem();
        if (item != null && !item.isClaimed()) {
            item.setClaimed(true);
            foundItemRepo.save(item);

            // 🎁 REWARD STAGE 2: Item Returned (+50 pts)
            giveReward(item.getUser(), POINTS_RETURN);
        }

        claim.setStatus(LostClaim.ClaimStatus.CLAIMED);
        return lostClaimRepo.save(claim);
    }

    // --- UTILITIES ---

    private void giveReward(User user, int points) {
        if (user != null) {
            user.setPoints(user.getPoints() + points);
            userRepo.save(user);
        }
    }

    private void saveAdminLog(LostClaim claim, Long adminId, AdminVerification.VerificationResult result, String note) {
        AdminVerification av = new AdminVerification();
        av.setClaim(claim);
        av.setAdmin(userRepo.findById(adminId).orElse(null));
        av.setResult(result);
        av.setNotes(note);
        adminVerificationRepo.save(av);
    }

    private String joinNonNull(String... parts) {
        StringBuilder sb = new StringBuilder();
        for (String part : parts) if (part != null) sb.append(part).append(" ");
        return sb.toString().trim();
    }

    @Override
    public Optional<LostClaim> findById(Long id) { return lostClaimRepo.findById(id); }
}