package com.beewear.api.infrastructure.adapter.persistence.repository;

import com.beewear.api.infrastructure.adapter.persistence.models.RegionJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringRegionRepository extends JpaRepository<RegionJpaModel, UUID> {
}
