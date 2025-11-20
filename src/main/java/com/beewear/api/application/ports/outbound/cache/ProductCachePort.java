package com.beewear.api.application.ports.outbound.cache;

import com.beewear.api.domain.entities.Product;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface ProductCachePort {
    void addProduct(Product product);
    void removeProduct(UUID productId);
    void updateProduct(Product product);
    Map<UUID, Product> getProducts(Set<UUID> productIds);
}
