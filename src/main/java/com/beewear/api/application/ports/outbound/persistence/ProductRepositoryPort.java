package com.beewear.api.application.ports.outbound.persistence;

import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.valueobject.UploadedImage;

import java.util.List;

public interface ProductRepositoryPort {
    Product save(Product product);
    Product updateImageUrls(Product product, List<UploadedImage> uploadedImages);
}
