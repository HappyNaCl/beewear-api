package com.beewear.api.infrastructure.adapter.persistence;

import com.beewear.api.application.ports.outbound.persistence.ProductRepositoryPort;
import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.valueobject.UploadedImage;
import com.beewear.api.infrastructure.adapter.persistence.mappers.ProductJpaMapper;
import com.beewear.api.infrastructure.adapter.persistence.models.ProductImageJpaModel;
import com.beewear.api.infrastructure.adapter.persistence.models.ProductJpaModel;
import com.beewear.api.infrastructure.adapter.persistence.repositories.SpringProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Repository
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private SpringProductRepository productRepository;
    private ProductJpaMapper productJpaMapper;

    @Override
    public Product save(Product product) {
        ProductJpaModel jpaModel = productJpaMapper.toJpaModel(product);
        ProductJpaModel savedModel = productRepository.save(jpaModel);
        return productJpaMapper.toDomain(savedModel);
    }

    @Override
    public Product updateImageUrls(Product product, List<UploadedImage> uploadedImages) {
        List<ProductImageJpaModel> productImageJpaModels = new ArrayList<>();

        for (UploadedImage uploadedImage : uploadedImages) {
            ProductImageJpaModel imageJpaModel = new ProductImageJpaModel();
            imageJpaModel.setImageUrl(uploadedImage.getSecureUrl());
            imageJpaModel.setProduct(productJpaMapper.toJpaModel(product));
            imageJpaModel.setPublicId(uploadedImage.getPublicId());
            productImageJpaModels.add(imageJpaModel);
        }

        ProductJpaModel productJpaModel = productJpaMapper.toJpaModel(product);
        productJpaModel.setProductImages(productImageJpaModels);

        ProductJpaModel savedModel = productRepository.save(productJpaModel);
        return productJpaMapper.toDomain(savedModel);
    }
}
