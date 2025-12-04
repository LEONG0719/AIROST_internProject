package com.project.airost.controller;

import com.project.airost.domain.FoundItem;
import com.project.airost.domain.User;
import com.project.airost.repository.FoundItemRepository;
import com.project.airost.repository.UserRepository;
import com.project.airost.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api/found-items")
public class FoundItemController {

    private final FoundItemRepository repository;
    private final UserRepository userRepository; // Needed to find the user
    private final FileStorageService fileStorageService;


    @GetMapping
    public List<FoundItem> listActive() {
        return repository.findByClaimedFalse();
    }

    @PostMapping
    public ResponseEntity<FoundItem> create(@RequestBody FoundItem item) {
        FoundItem saved = repository.save(item);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/browse/claimed")
    public List<FoundItem> getClaimedItemsOnly() {
        return repository.findByClaimedTrueOrderByFoundAtDesc();
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<FoundItem> reportFoundItem(
            // We can reuse LostClaimRequest logic or just map params manually for simplicity
            @RequestParam("userId") Long userId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("color") String color,
            @RequestParam("brand") String brand,
            @RequestParam(value = "specialMarking", required = false) String specialMarking,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FoundItem item = new FoundItem();
        item.setUser(user);
        item.setTitle(title);
        item.setDescription(description);
        item.setCategory(category);
        item.setColor(color);
        item.setBrand(brand);
        item.setSpecialMarking(specialMarking);
        item.setFoundAt(Instant.now());
        item.setClaimed(false);

        // Handle Image Upload
        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(image);
            item.setImageUrl(imageUrl);
        }

        FoundItem saved = repository.save(item);
        return ResponseEntity.ok(saved);
    }
}

