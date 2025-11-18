package com.beewear.api.application.ports.inbound.product;

import com.beewear.api.application.services.dto.ProductDto;
import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import com.beewear.api.domain.valueobject.ProductImageFile;

import java.util.List;
import java.util.UUID;

public interface CreateProductUseCase {
    ProductDto createProduct(
            String name,
            String description,
            double price,
            Gender forGender,
            ProductCategory productCategory,
            UUID creatorId,
            List<ProductImageFile> images
    );
}
