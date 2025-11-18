package com.beewear.api.application.services;

import com.beewear.api.application.ports.inbound.product.CreateProductUseCase;
import com.beewear.api.application.ports.inbound.product.GetRecentProductsUseCase;
import com.beewear.api.application.ports.outbound.cache.ProductCachePort;
import com.beewear.api.application.ports.outbound.cache.RecentProductsCachePort;
import com.beewear.api.application.ports.outbound.documents.ProductDocumentPort;
import com.beewear.api.application.ports.outbound.persistence.ProductRepositoryPort;
import com.beewear.api.application.ports.outbound.s3.ImageUploaderPort;
import com.beewear.api.application.services.dto.ProductDto;
import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import com.beewear.api.domain.exceptions.NoImageException;
import com.beewear.api.domain.valueobject.ProductImageFile;
import com.beewear.api.domain.valueobject.UploadedImage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ProductService implements CreateProductUseCase, GetRecentProductsUseCase {
    private final ImageUploaderPort imageUploader;
    private final ProductRepositoryPort productRepository;
    private final ProductDocumentPort productDocumentPort;
    private final ProductCachePort productCachePort;
    private final RecentProductsCachePort recentProductsCachePort;

    @Transactional
    @Override
    public ProductDto createProduct(String name,
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

        Product productWithImage = productRepository.updateImageUrls(savedProduct, uploadedImages);
        productDocumentPort.addProduct(productWithImage);

        productCachePort.addProduct(productWithImage);
        recentProductsCachePort.addProduct(productWithImage.getId().toString(),
                Instant.now().getEpochSecond());

        return ProductDto.fromProduct(productWithImage);
    }

    @Override
    public List<ProductDto> getRecentProducts(int limit) {
        Set<String> productIds = recentProductsCachePort.getRecentProducts(limit);

        Set<UUID> uuidProductIds = new LinkedHashSet<>();
        for (String id : productIds) {
            uuidProductIds.add(UUID.fromString(id));
        }

        return resolveProductIds(limit, uuidProductIds, null);
    }

    @Override
    public List<ProductDto> getRecentProducts(int limit, Instant lastTimestamp) {
        Set<String> productIds = recentProductsCachePort.getRecentProducts(limit, lastTimestamp.getEpochSecond());

        Set<UUID> uuidProductIds = new LinkedHashSet<>();
        for (String id : productIds) {
            uuidProductIds.add(UUID.fromString(id));
        }

        return resolveProductIds(limit, uuidProductIds, lastTimestamp);
    }

    private List<ProductDto> resolveProductIds(int limit, Set<UUID> productIds, Instant lastTimestamp) {
        if(productIds.isEmpty()) {
            if(lastTimestamp == null) {
                productIds = productRepository.getRecentProductIds(limit);
            }
            else {
                productIds = productRepository.getRecentProductIds(limit, lastTimestamp);
            }

            if (productIds.isEmpty()) {
                return List.of();
            }
        }

        Map<UUID, Product> productsMap = productCachePort.getProducts(productIds);

        List<Product> products = new ArrayList<>();
        for (UUID id : productIds) {
            Product p = productsMap.get(id);

            if (p == null) {
                p = productRepository.findById(id);
                if (p != null) {
                    productCachePort.addProduct(p);
                }
            }

            if (p != null) {
                products.add(p);
            }
        }

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(ProductDto.fromProduct(product));
        }

        return productDtos;
    }
}
