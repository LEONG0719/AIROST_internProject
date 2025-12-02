package com.project.airost.controller;

import com.project.airost.domain.FoundItem;
import com.project.airost.repository.FoundItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/found-items")
public class FoundItemController {

    private final FoundItemRepository repository;

    public FoundItemController(FoundItemRepository repository) {
        this.repository = repository;
    }

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
}
