package com.beewear.api.infrastructure.adapter.persistence.repositories;

import com.beewear.api.infrastructure.adapter.persistence.models.ProductJpaModel;
import com.beewear.api.domain.entities.enums.ProductStatus; // Make sure you have this Enum
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Import Param for safety

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SpringProductRepository extends JpaRepository<ProductJpaModel, UUID> {
    
    // ... (Keep your existing methods: findRecentProducts, findRecentProductsBefore, findDetailById) ...
    @Query("SELECT p FROM ProductJpaModel p ORDER BY p.createdAt DESC")
    List<ProductJpaModel> findRecentProducts(Pageable pageable);

    @Query("SELECT p FROM ProductJpaModel p WHERE p.createdAt < :lastTimestamp ORDER BY p.createdAt DESC")
    List<ProductJpaModel> findRecentProductsBefore(@Param("lastTimestamp") Instant lastTimestamp, Pageable pageable);

    @Query("""
    SELECT p FROM ProductJpaModel p
    LEFT JOIN FETCH p.productImages
    LEFT JOIN FETCH p.creator
    WHERE p.id = :productId
    """)
    ProductJpaModel findDetailById(@Param("productId") UUID productId);


    @Query("SELECT p FROM ProductJpaModel p LEFT JOIN FETCH p.productImages WHERE p.creator.id = :creatorId ORDER BY p.createdAt DESC")
    List<ProductJpaModel> findByCreatorIdOrderByCreatedAtDesc(@Param("creatorId") UUID creatorId);

    long countByCreatorIdAndStatus(UUID creatorId, ProductStatus status);
}