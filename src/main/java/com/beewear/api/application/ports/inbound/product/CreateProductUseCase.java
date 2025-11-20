package com.beewear.api.application.ports.inbound.product;

import com.beewear.api.application.services.dto.CreatedProductDto;
import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import com.beewear.api.domain.valueobject.ProductImageFile;

import java.util.List;
import java.util.UUID;

public interface CreateProductUseCase {
    CreatedProductDto createProduct(
            String name,
            String description,
            Double price,
            Gender forGender,
            ProductCategory productCategory,
            UUID creatorId,
            List<ProductImageFile> images
    );
}
