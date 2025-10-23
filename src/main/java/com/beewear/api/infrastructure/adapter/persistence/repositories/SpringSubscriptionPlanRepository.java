package com.beewear.api.infrastructure.adapter.persistence.repositories;

import com.beewear.api.infrastructure.adapter.persistence.models.SubscriptionPlanJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringSubscriptionPlanRepository extends JpaRepository<SubscriptionPlanJpaModel, UUID> {
}
