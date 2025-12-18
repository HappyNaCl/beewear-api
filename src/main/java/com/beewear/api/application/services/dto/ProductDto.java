package com.beewear.api.application.services.dto;

import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import com.beewear.api.domain.entities.enums.ProductStatus;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ProductDto {
    private UUID id;
    private String name;
    private Double price;
    private Gender forGender;
    private ProductCategory category;
    private String imageUrl;
    private UUID creatorId;
    private ProductStatus status;
    private Instant createdAt;
    private String description;


    public static ProductDto fromProduct(Product product) {
        ProductDto dto = new ProductDto();
        dto.id = product.getId();
        dto.name = product.getName();
        dto.price = product.getPrice();
        dto.forGender = product.getForGender();
        dto.category = product.getProductCategory();
        if (product.getProductImages() != null && !product.getProductImages().isEmpty()) {
            dto.imageUrl = product.getProductImages().get(0).getImageUrl();
        } else {
            dto.imageUrl = ""; 
        }
        dto.description = product.getDescription();
        dto.creatorId = product.getCreatorId();
        dto.status = product.getStatus();
        dto.createdAt = product.getCreatedAt();
        return dto;
    }
}
