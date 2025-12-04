package com.project.airost.service;

import com.project.airost.domain.LostClaim;

import java.util.Optional;

public interface LostClaimService {
    LostClaim submitClaim(LostClaim claim); // will run the 3-layer logic and persist
    Optional<LostClaim> findById(Long id);
    // admin actions
    LostClaim markApproved(Long claimId, Long adminId, String note);
    LostClaim markRejected(Long claimId, Long adminId, String note);
    LostClaim confirmReturn(Long claimId, Long userId);
    LostClaim submitProof(Long claimId, String studentIdNumber, String idImageUrl, String receiptUrl);
}
