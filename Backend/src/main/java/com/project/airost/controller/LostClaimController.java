package com.project.airost.controller;

import com.project.airost.domain.LostClaim;
import com.project.airost.domain.User;
import com.project.airost.dto.LostClaimRequest;
import com.project.airost.repository.LostClaimRepository;
import com.project.airost.service.FileStorageService;
import com.project.airost.service.LostClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor // ✅ Uses Lombok to generate constructor automatically
// @CrossOrigin is not needed if you have Global SecurityConfig, but keeping it is fine.
public class LostClaimController {

    private final LostClaimService claimService;
    private final FileStorageService fileStorageService;
    private final LostClaimRepository claimRepository; // ✅ Added Repository

    // 1. Submit Claim (For Users)
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<LostClaim> submitClaim(
            @RequestPart("data") LostClaimRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        // 1. Image Upload
        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(image);
            request.setImageUrl(imageUrl);
        }

        // 2. Map DTO -> Entity
        LostClaim claim = new LostClaim();
        User user = new User();
        user.setId(request.getUserId());
        claim.setUser(user);

        claim.setImageUrl(request.getImageUrl());
        claim.setDescription(request.getDescription());
        claim.setCategory(request.getCategory());
        claim.setColor(request.getColor());
        claim.setBrand(request.getBrand());
        claim.setRemarks(request.getRemarks());
        claim.setSpecialMarking(request.getSpecialMarking());

        // 3. Submit logic (AI)
        LostClaim saved = claimService.submitClaim(claim);
        return ResponseEntity.ok(saved);
    }

    // 2. Get Claim Details
    @GetMapping("/{id}")
    public ResponseEntity<LostClaim> getClaim(@PathVariable Long id) {
        return claimRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ==========================================
    // 👑 ADMIN ENDPOINTS
    // ==========================================

    // 3. Get ALL Claims (For Admin Dashboard Table)
    @GetMapping("/admin/all")
    public List<LostClaim> getAllClaimsForAdmin() {
        // You can add sorting here if you want newest first
        return claimRepository.findAll();
    }

    // 4. Get Only "Pending" Claims (For Admin Notifications/Tasks)
    @GetMapping("/admin/pending")
    public List<LostClaim> getPendingClaims() {
        // ✅ Fixed: Now actually queries DB for NEEDS_MANUAL_CHECK
        return claimRepository.findByStatus(LostClaim.ClaimStatus.NEEDS_MANUAL_CHECK);
    }

    // 5. Admin Verify - Approve
    @PostMapping("/{id}/admin/approve")
    public ResponseEntity<?> approveClaim(
            @PathVariable Long id,
            @RequestParam Long adminId,
            @RequestParam String note) {

        LostClaim updated = claimService.markApproved(id, adminId, note);
        return ResponseEntity.ok(updated);
    }

    // 6. Admin Verify - Reject
    @PostMapping("/{id}/admin/reject")
    public ResponseEntity<?> rejectClaim(
            @PathVariable Long id,
            @RequestParam Long adminId,
            @RequestParam String note) {

        LostClaim updated = claimService.markRejected(id, adminId, note);
        return ResponseEntity.ok(updated);
    }

    // 7. Delete Claim (Admin Feature)
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteClaim(@PathVariable Long id) {
        if (!claimRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        claimRepository.deleteById(id);
        return ResponseEntity.ok("Claim deleted successfully");
    }
}