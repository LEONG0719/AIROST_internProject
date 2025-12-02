package com.project.airost.repository;

import com.project.airost.domain.FoundItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoundItemImageRepository extends JpaRepository<FoundItemImage, Long> {
    List<FoundItemImage> findByFoundItemId(Long foundItemId);
}
