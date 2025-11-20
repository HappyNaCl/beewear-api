package com.beewear.api.application.services.dto;

import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class DetailedProductDto {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Gender forGender;
    private ProductCategory category;
    private List<String> imageUrls;
    private Instant createdAt;
    private ProductUserDto creator;
}
