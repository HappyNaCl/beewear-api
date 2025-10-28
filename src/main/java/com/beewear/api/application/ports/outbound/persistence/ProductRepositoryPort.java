package com.beewear.api.application.ports.outbound.persistence;

import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.valueobject.UploadedImage;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductRepositoryPort {
    Product save(Product product);
    Product updateImageUrls(Product product, List<UploadedImage> uploadedImages);
    Set<UUID> getRecentProductIds(int limit);
    Set<UUID> getRecentProductIds(int limit, Instant lastTimestamp);
    Product findById(UUID id);
}
