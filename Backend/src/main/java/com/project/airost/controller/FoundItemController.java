package com.project.airost.controller;

import com.project.airost.domain.FoundItem;
import com.project.airost.domain.User;
import com.project.airost.repository.FoundItemRepository;
import com.project.airost.repository.UserRepository;
import com.project.airost.service.FileStorageService;
import com.project.airost.service.FoundItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@RequestMapping("/api/found-items")
public class FoundItemController {

    private final FoundItemRepository repository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final FoundItemService foundItemService; // ✅ Injected Service

    // 1. Get Active Unclaimed Items (Public Feed)
    @GetMapping
    public List<FoundItem> listActive() {
        // You can also use foundItemService.findAllUnclaimed() here if preferred
        return repository.findByClaimedFalse();
    }

    // 2. Simple Create (JSON only - mainly for testing)
    @PostMapping
    public ResponseEntity<FoundItem> create(@RequestBody FoundItem item) {
        FoundItem saved = repository.save(item);
        return ResponseEntity.ok(saved);
    }

    // 3. Get Claimed Items History
    @GetMapping("/browse/claimed")
    public List<FoundItem> getClaimedItemsOnly() {
        return repository.findByClaimedTrueOrderByFoundAtDesc();
    }

    // 4. REPORT FOUND ITEM (Multipart + Logic)
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<FoundItem> reportFoundItem(
            @RequestParam("userId") Long userId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("color") String color,
            @RequestParam("brand") String brand,
            @RequestParam(value = "specialMarking", required = false) String specialMarking,
            @RequestParam(value = "location", required = false) String location,

            // ✅ NEW: Required Drop-off Time
            @RequestParam("dropOffTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dropOffTime,

            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        // A. Construct the basic Item object
        FoundItem item = new FoundItem();
        item.setTitle(title);
        item.setDescription(description);
        item.setCategory(category);
        item.setColor(color);
        item.setBrand(brand);
        item.setSpecialMarking(specialMarking);
        item.setLocation(location);
        item.setFoundAt(Instant.now());

        // B. Handle Image Upload
        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(image);
            item.setImageUrl(imageUrl);
        }

        // C. Call Service to handle Linking & Scheduling
        // This sets the User, DropOffTime, and defaults (claimed=false)
        FoundItem saved = foundItemService.reportFoundItem(item, userId, dropOffTime);

        // D. ✅ REWARD UPDATE: Award 10 Points for Reporting
        // (25 pts will be given later if a Match is Approved, and 50 pts if Claimed)
        User user = saved.getUser();
        int currentPoints = user.getPoints() == null ? 0 : user.getPoints();
        user.setPoints(currentPoints + 10);
        userRepository.save(user);

        return ResponseEntity.ok(saved);
    }

    // 5. Admin: Get ALL Items
    @GetMapping("/admin/all")
    public List<FoundItem> getAllItemsForAdmin() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "foundAt"));
    }

    // 6. Admin: Delete Item
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteFoundItem(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok("Item deleted successfully");
    }
}