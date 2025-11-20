package com.beewear.api.application.ports.outbound.cache;

import com.beewear.api.domain.entities.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductDetailCachePort {
    void invalidate(UUID id);
    Optional<Product> getProductDetail(UUID id);
    void setProductDetail(Product product);
}
