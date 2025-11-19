package com.beewear.api.application.ports.inbound.product;

import com.beewear.api.application.services.dto.ProductDto;
import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchProductUseCase {
    List<ProductDto> searchProducts(String query, Double minPrice, Double maxPrice, Gender gender, ProductCategory category, Pageable pageable);
}
