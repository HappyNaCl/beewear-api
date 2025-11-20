package com.beewear.api.application.ports.outbound.cache;

import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;

import java.util.List;
import java.util.UUID;

public interface SearchProductCachePort {
    void setProducts(String query, Double minPrice, Double maxPrice,
                     Gender gender, ProductCategory productCategory,
                     Integer size, Integer page,
                     List<UUID> productIds);
    List<UUID> getProducts(String query, Double minPrice, Double maxPrice,
                           Gender gender, ProductCategory productCategory,
                           Integer size,  Integer page);
    void invalidate(String query, Double minPrice, Double maxPrice,
                    Gender gender, ProductCategory productCategory, Integer size, Integer page);
}
