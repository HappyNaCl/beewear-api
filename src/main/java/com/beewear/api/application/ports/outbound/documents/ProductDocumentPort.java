package com.beewear.api.application.ports.outbound.documents;

import com.beewear.api.domain.entities.Product;

import java.util.List;

public interface ProductDocumentPort {
    List<Product> searchProducts(String query); // TODO: Add filters
    void addProduct(Product product);
    void removeProduct(Product product);
    void updateProduct(Product product);
}
