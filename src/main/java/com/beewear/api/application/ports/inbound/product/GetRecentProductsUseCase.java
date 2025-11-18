package com.beewear.api.application.ports.inbound.product;

import com.beewear.api.application.services.dto.ProductDto;
import com.beewear.api.domain.entities.Product;

import java.time.Instant;
import java.util.List;

public interface GetRecentProductsUseCase {
    List<ProductDto> getRecentProducts(int limit);
    List<ProductDto> getRecentProducts(int limit, Instant lastTimestamp);
}
