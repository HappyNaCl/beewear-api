package com.beewear.api.infrastructure.adapter.persistence.repositories;

import com.beewear.api.infrastructure.adapter.persistence.models.ProductJpaModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SpringProductRepository extends JpaRepository<ProductJpaModel, UUID> {
    @Query("SELECT p FROM ProductJpaModel p ORDER BY p.createdAt DESC")
    List<ProductJpaModel> findRecentProducts(Pageable pageable);

    @Query("SELECT p FROM ProductJpaModel p WHERE p.createdAt < :lastTimestamp ORDER BY p.createdAt DESC")
    List<ProductJpaModel> findRecentProductsBefore(Instant lastTimestamp, Pageable pageable);

    @Query("""
    SELECT p FROM ProductJpaModel p
    LEFT JOIN FETCH p.productImages
    LEFT JOIN FETCH p.creator
    WHERE p.id = :productId
    """)
    ProductJpaModel findDetailById(UUID productId);
}
