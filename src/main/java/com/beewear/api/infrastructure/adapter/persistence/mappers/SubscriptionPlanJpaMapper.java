package com.beewear.api.infrastructure.adapter.persistence.mappers;

import com.beewear.api.domain.entities.SubscriptionPlan;
import com.beewear.api.infrastructure.adapter.persistence.models.SubscriptionPlanJpaModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionPlanJpaMapper {
    SubscriptionPlan toDomain(SubscriptionPlanJpaModel model);

    SubscriptionPlanJpaModel toJpaModel(SubscriptionPlan domain);
}