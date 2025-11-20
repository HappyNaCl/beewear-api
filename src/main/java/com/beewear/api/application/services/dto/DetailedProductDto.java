package com.beewear.api.application.services.dto;

import com.beewear.api.domain.entities.ProductImage;
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

    public static DetailedProductDto fromProduct(com.beewear.api.domain.entities.Product product) {
        DetailedProductDto dto = new DetailedProductDto();
        dto.id = product.getId();
        dto.name = product.getName();
        dto.description = product.getDescription();
        dto.price = product.getPrice();
        dto.forGender = product.getForGender();
        dto.category = product.getProductCategory();
        dto.imageUrls = product.getProductImages().stream()
                .map(ProductImage::getImageUrl)
                .toList();
        dto.createdAt = product.getCreatedAt();
        dto.creator = ProductUserDto.fromUser(product.getCreator());
        return dto;
    }
}
