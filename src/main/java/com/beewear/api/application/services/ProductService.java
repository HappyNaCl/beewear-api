package com.beewear.api.application.services;

import com.beewear.api.application.ports.inbound.product.CreateProductUseCase;
import com.beewear.api.application.ports.inbound.product.GetProductDetailUseCase;
import com.beewear.api.application.ports.inbound.product.GetRecentProductsUseCase;
import com.beewear.api.application.ports.inbound.product.SearchProductUseCase;
import com.beewear.api.application.ports.outbound.cache.ProductCachePort;
import com.beewear.api.application.ports.outbound.cache.ProductDetailCachePort;
import com.beewear.api.application.ports.outbound.cache.RecentProductsCachePort;
import com.beewear.api.application.ports.outbound.cache.SearchProductCachePort;
import com.beewear.api.application.ports.outbound.documents.ProductDocumentPort;
import com.beewear.api.application.ports.outbound.events.ProductEventPublisherPort;
import com.beewear.api.application.ports.outbound.persistence.ProductRepositoryPort;
import com.beewear.api.application.ports.outbound.s3.ImageUploaderPort;
import com.beewear.api.application.services.dto.CreatedProductDto;
import com.beewear.api.application.services.dto.DetailedProductDto;
import com.beewear.api.application.services.dto.ProductDto;
import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import com.beewear.api.domain.events.ProductCreatedEvent;
import com.beewear.api.domain.exceptions.InvalidProductPriceException;
import com.beewear.api.domain.exceptions.NoImageException;
import com.beewear.api.domain.exceptions.ProductNotFoundException;
import com.beewear.api.domain.valueobject.ProductImageFile;
import com.beewear.api.domain.valueobject.UploadedImage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.beewear.api.domain.entities.enums.ProductStatus;

import java.time.Instant;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductService implements CreateProductUseCase, GetRecentProductsUseCase,
        SearchProductUseCase, GetProductDetailUseCase {
    private final ImageUploaderPort imageUploader;

    private final ProductRepositoryPort productRepository;

    private final ProductDocumentPort productDocumentPort;

    private final ProductCachePort productCachePort;
    private final SearchProductCachePort searchProductCachePort;
    private final RecentProductsCachePort recentProductsCachePort;
    private final ProductDetailCachePort productDetailCachePort;

    private final ProductEventPublisherPort productEventPublisher;

    @Transactional
    @Override
    public CreatedProductDto createProduct(
        String name, String description,
        Double price, Gender forGender,
        ProductCategory productCategory,
            UUID creatorId, List<ProductImageFile> images) {

        if(images.isEmpty()) {
            throw new NoImageException();
        }

        if(price < 0 || price > 999999999) {
            throw new InvalidProductPriceException();
        }

        Product product = Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .forGender(forGender)
                .productCategory(productCategory)
                .creatorId(creatorId)
                .status(ProductStatus.ACTIVE)
                .build();

        Product savedProduct = productRepository.save(product);

        List<UploadedImage> uploadedImages = new ArrayList<>();

        for (ProductImageFile image : images) {
            UploadedImage uploadedImage = imageUploader.uploadImage(image.fileName(), image.data(), String.format("products/%s", savedProduct.getId()));
            uploadedImages.add(uploadedImage);
        }

        Product productWithImage = productRepository.updateImageUrls(savedProduct, uploadedImages);
        productDocumentPort.addProduct(productWithImage);

        productEventPublisher.publish(new ProductCreatedEvent(productWithImage.getId()));

        return CreatedProductDto.fromProduct(productWithImage);
    }

    @Override
    public List<ProductDto> getRecentProducts(int limit) {
        Set<String> productIds = recentProductsCachePort.getRecentProducts(limit);

        Set<UUID> uuidProductIds = new LinkedHashSet<>();
        for (String id : productIds) {
            uuidProductIds.add(UUID.fromString(id));
        }

        return resolveRecentProductIds(limit, uuidProductIds, null);
    }

    @Override
    public List<ProductDto> getRecentProducts(int limit, Instant lastTimestamp) {
        Set<String> productIds = recentProductsCachePort.getRecentProducts(limit, lastTimestamp.getEpochSecond());

        Set<UUID> uuidProductIds = new LinkedHashSet<>();
        for (String id : productIds) {
            uuidProductIds.add(UUID.fromString(id));
        }

        return resolveRecentProductIds(limit, uuidProductIds, lastTimestamp);
    }

    @Override
    public List<ProductDto> searchProducts(String query, Double minPrice, Double maxPrice, Gender gender, ProductCategory category, Pageable pageable) {
        List<UUID> cachedProductIds = searchProductCachePort.getProducts(query, minPrice, maxPrice, gender,
                category, pageable.getPageSize(), pageable.getPageNumber());

        if(!cachedProductIds.isEmpty()) {
            Set<UUID> productIds = new LinkedHashSet<>(cachedProductIds);
            return getProducts(productIds);
         }

        List<Product> products = productDocumentPort.searchProducts(query, minPrice, maxPrice, gender, category, pageable);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(ProductDto.fromProduct(product));
        }

        List<UUID> productIdList = new ArrayList<>();
        for (ProductDto dto : productDtos) {
            productIdList.add(dto.getId());
        }

        searchProductCachePort.setProducts(query, minPrice, maxPrice, gender,
                category, pageable.getPageSize(), pageable.getPageNumber(), productIdList);

        return productDtos;
    }

    private List<ProductDto> getProducts(Set<UUID> productIds) {
        Map<UUID, Product> productsMap = productCachePort.getProducts(productIds);

        List<Product> products = new ArrayList<>();
        for (UUID id : productIds) {
            Product p = productsMap.get(id);

            if (p == null) {
                p = productRepository.findById(id).orElse(null);
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

    private List<ProductDto> resolveRecentProductIds(int limit, Set<UUID> productIds, Instant lastTimestamp) {
        if(productIds.isEmpty()) {
            List<Product> products;
            if(lastTimestamp == null) {
                products = productRepository.getRecentProducts(limit);
            }
            else {
                products = productRepository.getRecentProducts(limit, lastTimestamp);
            }

            if (products.isEmpty()) {
                log.debug("No recent products found in database");
                return List.of();
            }

            for(Product product : products) {
                recentProductsCachePort.addProduct(product.getId().toString(),
                        product.getCreatedAt().getEpochSecond());
            }

            return products.stream().map(ProductDto::fromProduct).toList();
        }

        return getProducts(productIds);
    }

    @Override
    public DetailedProductDto getProductDetail(UUID id) {
        Optional<Product> product = productDetailCachePort.getProductDetail(id);
        if(product.isPresent()) {
            return DetailedProductDto.fromProduct(product.get());
        }

        product = productRepository.findDetailById(id);
        if(product.isEmpty()) {
            throw new ProductNotFoundException(id);
        }

        productDetailCachePort.setProductDetail(product.get());
        return DetailedProductDto.fromProduct(product.get());
    }
}
