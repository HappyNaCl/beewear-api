package com.beewear.api.infrastructure.adapter.elasticsearch;

import com.beewear.api.application.ports.outbound.documents.ProductDocumentPort;
import com.beewear.api.domain.entities.Product;
import com.beewear.api.infrastructure.adapter.elasticsearch.models.ProductDocument;
import com.beewear.api.infrastructure.adapter.elasticsearch.repository.ElasticsearchProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Repository
public class ProductElasticsearchAdapter implements ProductDocumentPort {

    private ElasticsearchProductRepository repo;

    @Override
    public List<Product> searchProducts(String query) {
        return List.of();
    }

    @Override
    public void addProduct(Product product) {
        ProductDocument doc = new ProductDocument();
        doc.setId(product.getId());
        doc.setName(product.getName());
        doc.setDescription(product.getDescription());
        doc.setPrice(product.getPrice());
        doc.setFirstImageUrl(product.getProductImages().get(0).getImageUrl());
        doc.setProductCategory(product.getProductCategory());
        doc.setForGender(product.getForGender());
        doc.setCreatorId(product.getCreatorId());
        doc.setCreatedAt(product.getCreatedAt());

        repo.save(doc);
    }

    @Override
    public void removeProduct(Product product) {

    }

    @Override
    public void updateProduct(Product product) {

    }
}
