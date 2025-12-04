package com.project.airost.repository;

import com.project.airost.domain.FoundItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {
    List<FoundItem> findByClaimedFalse();
    // add query methods (by category, color, etc) if needed\
    List<FoundItem> findByClaimedTrueOrderByFoundAtDesc();
    long countByUserId(Long userId);
    List<FoundItem> findByUserId(Long userId);
    long countByClaimedTrue();

}
