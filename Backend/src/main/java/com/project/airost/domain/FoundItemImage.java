package com.project.airost.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "found_item_images")
public class FoundItemImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=2000)
    private String imageUrl; // stored in S3 or server

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "found_item_id")
    private FoundItem foundItem;

    // getters/setters
}
