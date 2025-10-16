package com.beewear.api.infrastructure.adapter.persistence.repositories;

import com.beewear.api.infrastructure.adapter.persistence.models.ProductJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringProductRepository extends JpaRepository<ProductJpaModel, UUID> {

}
