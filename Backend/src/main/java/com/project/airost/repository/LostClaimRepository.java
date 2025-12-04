package com.project.airost.repository;

import com.project.airost.domain.LostClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LostClaimRepository extends JpaRepository<LostClaim, Long> {
    List<LostClaim> findByUserId(Long userId);
    List<LostClaim> findByStatus(LostClaim.ClaimStatus status);
    long countByUserId(Long userId);

    // Count successful matches (APPROVED claims)
    long countByUserIdAndStatus(Long userId, LostClaim.ClaimStatus status);

}