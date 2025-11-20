package com.beewear.api.application.ports.inbound.product;

import com.beewear.api.application.services.dto.DetailedProductDto;

import java.util.Optional;
import java.util.UUID;

public interface GetProductDetailUseCase {
    DetailedProductDto getProductDetail(UUID id);
}
