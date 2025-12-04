package com.project.airost.controller;

import com.project.airost.domain.LostClaim;
import com.project.airost.domain.User;
import com.project.airost.dto.LostClaimRequest;
import com.project.airost.service.FileStorageService;
import com.project.airost.service.LostClaimService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5174", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api/claims")
public class LostClaimController {

    private final LostClaimService claimService;
    private final FileStorageService fileStorageService;

    public LostClaimController(LostClaimService claimService, FileStorageService fileStorageService) {
        this.claimService = claimService;
        this.fileStorageService = fileStorageService;
    }

    // 1. Submit Claim (For Users)
    @PostMapping(consumes = {"multipart/form-data"}) // Crucial!
    public ResponseEntity<LostClaim> submitClaim(
            @RequestPart("data") LostClaimRequest request,  // The JSON part
            @RequestPart(value = "image", required = false) MultipartFile image // The File part
    ) {

        // 1. If image exists, save it and set the URL in the request object
        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(image);
            request.setImageUrl(imageUrl); // Make sure your DTO has setImageUrl()
        }

        // 2. Map DTO to Entity (Manual mapping or inside Service)
        LostClaim claim = new LostClaim();
        User user = new User();
        user.setId(request.getUserId());
        claim.setUser(user);

        claim.setImageUrl(request.getImageUrl()); // This now has the file path!
        claim.setDescription(request.getDescription());
        claim.setCategory(request.getCategory());
        claim.setColor(request.getColor());
        claim.setBrand(request.getBrand());
        claim.setRemarks(request.getRemarks());
        claim.setSpecialMarking(request.getSpecialMarking());

        // 3. Submit
        LostClaim saved = claimService.submitClaim(claim);
        return ResponseEntity.ok(saved);
    }

    // 2. Get All Claims Needing Verification (For Admin Dashboard)
    // You need to add findByStatus in your Service/Repo first!
    @GetMapping("/pending-verification")
    public ResponseEntity<List<LostClaim>> getPendingClaims() {
        // Implementation needed in Service: return repo.findByStatus(NEEDS_MANUAL_CHECK);
        // For now returning empty list placeholder
        return ResponseEntity.ok(List.of());
    }

    // 3. Admin Verify - Approve
    @PostMapping("/{id}/admin/approve")
    public ResponseEntity<?> approveClaim(
            @PathVariable Long id,
            @RequestParam Long adminId,
            @RequestParam String note) {

        LostClaim updated = claimService.markApproved(id, adminId, note);
        return ResponseEntity.ok(updated);
    }

    // 4. Admin Verify - Reject
    @PostMapping("/{id}/admin/reject")
    public ResponseEntity<?> rejectClaim(
            @PathVariable Long id,
            @RequestParam Long adminId,
            @RequestParam String note) {

        LostClaim updated = claimService.markRejected(id, adminId, note);
        return ResponseEntity.ok(updated);
    }
}