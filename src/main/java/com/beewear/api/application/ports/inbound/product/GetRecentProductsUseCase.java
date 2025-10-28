package com.beewear.api.application.ports.inbound.product;

import com.beewear.api.domain.entities.Product;

import java.time.Instant;
import java.util.List;

public interface GetRecentProductsUseCase {
    List<Product> getRecentProducts(int limit);
    List<Product> getRecentProducts(int limit, Instant lastTimestamp);
}
