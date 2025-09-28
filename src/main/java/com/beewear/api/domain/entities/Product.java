package com.beewear.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;

    private UUID creatorId;

    private Instant createdAt;
    private Instant updatedAt;
}
