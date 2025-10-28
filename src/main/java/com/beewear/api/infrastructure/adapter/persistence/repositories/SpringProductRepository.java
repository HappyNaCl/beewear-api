package com.beewear.api.infrastructure.adapter.persistence.repositories;

import com.beewear.api.infrastructure.adapter.persistence.models.ProductJpaModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SpringProductRepository extends JpaRepository<ProductJpaModel, UUID> {
    @Query("SELECT p.id FROM ProductJpaModel p ORDER BY p.createdAt DESC")
    List<UUID> findRecentProductIds(Pageable pageable);

    @Query("SELECT p.id FROM ProductJpaModel p WHERE p.createdAt < :lastTimestamp ORDER BY p.createdAt DESC")
    List<UUID> findRecentProductIdsBefore(@Param("lastTimestamp") Instant lastTimestamp, Pageable pageable);

}
