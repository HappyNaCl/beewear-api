package com.beewear.api.infrastructure.adapter.rest.controllers;

import com.beewear.api.application.ports.inbound.product.CreateProductUseCase;
import com.beewear.api.application.ports.inbound.product.GetRecentProductsUseCase;
import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.valueobject.ProductImageFile;
import com.beewear.api.infrastructure.adapter.rest.requests.CreateProductRequest;
import com.beewear.api.infrastructure.adapter.rest.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
@Tag(name = "Product API", description = "API for product management")
public class ProductController {
    private final CreateProductUseCase createProductUseCase;
    private final GetRecentProductsUseCase getRecentProductsUseCase;

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<Product>>> getRecentProducts(
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "lastTimestamp", required = false) Instant lastTimestamp
    ) {
        List<Product> products;

        if (lastTimestamp != null) {
            log.debug("Getting recent products for last timestamp {}", lastTimestamp);
            products = getRecentProductsUseCase.getRecentProducts(limit, lastTimestamp);
        } else {
            products = getRecentProductsUseCase.getRecentProducts(limit);
        }

        return ResponseEntity.ok(ApiResponse.success(200, products));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Product>> createProduct(
            @ModelAttribute CreateProductRequest req,
            @RequestPart("images") List<MultipartFile> images,
            @AuthenticationPrincipal UUID userId
            ) {

        List<ProductImageFile> imageFiles = images.stream().map(image -> {
            try {
                return ProductImageFile.newUpload(
                        image.getOriginalFilename(),
                        image.getBytes()
                );
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to read uploaded image bytes", e);
            }
        }).toList();

        createProductUseCase.createProduct(
                req.getName(),
                req.getDescription(),
                req.getPrice(),
                req.getGender(),
                req.getProductCategory(),
                userId,
                imageFiles
        );

        return ResponseEntity.ok(ApiResponse.success(201, null));
    }

}
