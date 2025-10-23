package com.beewear.api.domain.entities;

import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private UUID id;
    private UUID creatorId;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private Gender forGender;
    private ProductCategory productCategory;

    private User creator;
    private List<ProductImage> productImages;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
