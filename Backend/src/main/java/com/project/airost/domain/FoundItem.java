package com.project.airost.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "found_items")
public class FoundItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable=false)
    private String title; // short title

    @Column(length=2000)
    private String description; // text description for AI text match

    @Column(nullable=false)
    private String category;

    private String specialMarking;

    // === NEW: Primary Image URL (Simpler than List for basic views) ===
    private String imageUrl;

    private String color;
    private String brand;

    @OneToMany(mappedBy = "foundItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoundItemImage> images;

    private boolean claimed = false;

    private Instant foundAt = Instant.now();

    // getters / setters / constructors
}
