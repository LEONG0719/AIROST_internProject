package com.project.airost.controller;

import com.project.airost.domain.LostClaim;
import com.project.airost.domain.User;
import com.project.airost.dto.LostClaimRequest;
import com.project.airost.repository.LostClaimRepository;
import com.project.airost.repository.UserRepository;
import com.project.airost.service.FileStorageService;
import com.project.airost.service.LostClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = {"http://localhost:5173"}, allowedHeaders = "*", methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE})
@RequiredArgsConstructor
public class LostClaimController {

    private final LostClaimService claimService;
    private final FileStorageService fileStorageService;
    private final LostClaimRepository claimRepository;
    private final UserRepository userRepository; // ✅ Needed to update phone number

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

        // 2. Fetch User & Update Phone Number
        // We fetch the user FIRST so we can update their phone number if they provided one
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank()) {
            user.setPhoneNumber(request.getPhoneNumber());
            userRepository.save(user); // Save the new phone number
        }

        // 3. Map DTO -> Entity
        LostClaim claim = new LostClaim();
        claim.setUser(user); // Set the fetched user object

        claim.setImageUrl(request.getImageUrl());
        claim.setDescription(request.getDescription());
        claim.setCategory(request.getCategory());
        claim.setColor(request.getColor());
        claim.setBrand(request.getBrand());
        claim.setRemarks(request.getRemarks());
        claim.setSpecialMarking(request.getSpecialMarking());

        // ✅ MAP LOCATION (This was missing/unreachable in your code)
        claim.setLocation(request.getLocation());

        // 4. Submit logic (AI)
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

    @PostMapping("/{id}/upload-proof")
    public ResponseEntity<LostClaim> uploadProof(
            @PathVariable Long id,
            @RequestParam("studentIdNumber") String studentIdNumber,
            @RequestPart("idCardImage") MultipartFile idCardImage,
            @RequestPart(value = "receiptImage", required = false) MultipartFile receiptImage
    ) {
        // 1. Save ID Card Image
        String idCardUrl = fileStorageService.storeFile(idCardImage);

        // 2. Save Receipt Image (if provided)
        String receiptUrl = null;
        if (receiptImage != null && !receiptImage.isEmpty()) {
            receiptUrl = fileStorageService.storeFile(receiptImage);
        }

        // 3. Update Service
        LostClaim updated = claimService.submitProof(id, studentIdNumber, idCardUrl, receiptUrl);

        return ResponseEntity.ok(updated);
    }

    // ==========================================
    // 👑 ADMIN ENDPOINTS
    // ==========================================

    // 3. Get ALL Claims
    @GetMapping("/admin/all")
    public List<LostClaim> getAllClaimsForAdmin() {
        return claimRepository.findAll();
    }

    // 4. Get Only "Pending" Claims
    @GetMapping("/admin/pending")
    public List<LostClaim> getPendingClaims() {
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

    // 7. Delete Claim
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteClaim(@PathVariable Long id) {
        if (!claimRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        claimRepository.deleteById(id);
        return ResponseEntity.ok("Claim deleted successfully");
    }
}