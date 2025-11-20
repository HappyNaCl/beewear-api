package com.beewear.api.application.services.dto;

import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.entities.ProductImage;
import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class CreatedProductDto {
    private UUID id;
    private String name;
    private Double price;
    private Gender forGender;
    private ProductCategory category;
    private List<String> imageUrls;
    private Instant createdAt;

    public static CreatedProductDto fromProduct(Product product) {
        CreatedProductDto dto = new CreatedProductDto();
        dto.id = product.getId();
        dto.name = product.getName();
        dto.price = product.getPrice();
        dto.forGender = product.getForGender();
        dto.category = product.getProductCategory();
        dto.imageUrls = product.getProductImages().stream()
                .map(ProductImage::getImageUrl)
                .toList();
        dto.createdAt = product.getCreatedAt();
        return dto;
    }
}
