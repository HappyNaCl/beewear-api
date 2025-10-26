package com.beewear.api.infrastructure.adapter.elasticsearch.repository;

import com.beewear.api.infrastructure.adapter.elasticsearch.models.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface ElasticsearchProductRepository extends ElasticsearchRepository<ProductDocument, UUID> {
}
