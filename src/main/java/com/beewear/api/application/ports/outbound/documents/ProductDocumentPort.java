package com.beewear.api.application.ports.outbound.documents;

import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductDocumentPort {
    List<Product> searchProducts(String query, Double minPrice, Double maxPrice, Gender gender, ProductCategory category, Pageable pageable);
    void addProduct(Product product);
    void removeProduct(Product product);
    void updateProduct(Product product);
}
