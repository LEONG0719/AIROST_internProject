package com.project.airost.service;

import com.project.airost.domain.FoundItem;
import java.time.LocalDateTime;
import java.util.List;

public interface FoundItemService {
    // The main method for a user reporting an item
    FoundItem reportFoundItem(FoundItem item, Long finderId, LocalDateTime dropOffTime);

    // Helper to get all active items for the AI matching
    List<FoundItem> findAllUnclaimed();
}