package com.beewear.api.application.services;

import com.beewear.api.application.ports.inbound.product.CreateProductUseCase;
import com.beewear.api.application.ports.outbound.persistence.ProductRepositoryPort;
import com.beewear.api.application.ports.outbound.s3.ImageUploaderPort;
import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import com.beewear.api.domain.exceptions.NoImageException;
import com.beewear.api.domain.valueobject.ProductImageFile;
import com.beewear.api.domain.valueobject.UploadedImage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductService implements CreateProductUseCase {
    private final ImageUploaderPort imageUploader;
    private final ProductRepositoryPort productRepository;

    @Transactional
    @Override
    public Product createProduct(String name,
                              String description,
                              double price,
                              Gender forGender,
                              ProductCategory productCategory,
                              UUID creatorId,
                              List<ProductImageFile> images) {

        if(images.isEmpty()) {
            throw new NoImageException();
        }

        Product product = Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .forGender(forGender)
                .productCategory(productCategory)
                .creatorId(creatorId)
                .build();

        Product savedProduct = productRepository.save(product);

        List<UploadedImage> uploadedImages = new ArrayList<>();

        for (ProductImageFile image : images) {
            UploadedImage uploadedImage = imageUploader.uploadImage(image.fileName(), image.data(), String.format("products/%s", savedProduct.getId()));
            uploadedImages.add(uploadedImage);
        }

        return productRepository.updateImageUrls(savedProduct, uploadedImages);
    }
}
