package com.project.airost.service.Implem;

import com.project.airost.domain.FoundItem;
import com.project.airost.domain.User;
import com.project.airost.repository.FoundItemRepository;
import com.project.airost.repository.UserRepository;
import com.project.airost.service.FoundItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FoundItemServiceImpl implements FoundItemService {

    private final FoundItemRepository foundItemRepo;
    private final UserRepository userRepo;

    @Autowired
    public FoundItemServiceImpl(FoundItemRepository foundItemRepo, UserRepository userRepo) {
        this.foundItemRepo = foundItemRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public FoundItem reportFoundItem(FoundItem item, Long finderId, LocalDateTime dropOffTime) {
        // 1. Link the Finder (User)
        User finder = userRepo.findById(finderId)
                .orElseThrow(() -> new RuntimeException("Finder User ID not found"));
        item.setUser(finder);

        // 2. Set the Drop-Off Schedule (REQUIRED by your new logic)
        if (dropOffTime == null) {
            throw new IllegalArgumentException("You must provide a drop-off time for the claim office.");
        }
        item.setDropOffTime(dropOffTime);

        // 3. Set Defaults
        item.setClaimed(false);
        item.setDropOffTime(LocalDateTime.now()); // Or use input date if provided

        // 4. Save to Database
        return foundItemRepo.save(item);
    }

    @Override
    public List<FoundItem> findAllUnclaimed() {
        return foundItemRepo.findByClaimedFalse();
    }
}