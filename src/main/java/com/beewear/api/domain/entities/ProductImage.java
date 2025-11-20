package com.beewear.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    private UUID id;
    private UUID productId;
    private String publicId;
    private String imageUrl;

    private Product product;

    private Instant createdAt;
    private Instant updatedAt;
}
