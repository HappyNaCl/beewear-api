package com.beewear.api.application.services.dto;

import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.entities.ProductImage;
import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ProductDto {
    private UUID id;
    private String name;
    private Double price;
    private Gender forGender;
    private ProductCategory category;
    private List<String> imageUrls;

    public static ProductDto fromProduct(Product product) {
        ProductDto dto = new ProductDto();
        dto.id = product.getId();
        dto.name = product.getName();
        dto.price = product.getPrice();
        dto.forGender = product.getForGender();
        dto.category = product.getProductCategory();

        dto.imageUrls = product.getProductImages().stream()
                .map(ProductImage::getImageUrl)
                .toList();

        return dto;
    }
}
