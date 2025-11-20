package com.beewear.api.application.ports.inbound.product;

import com.beewear.api.application.services.dto.ProductDto;

import java.util.UUID;

public interface GetProductDetailUseCase {
    ProductDto getProductDetail(UUID id);
}
