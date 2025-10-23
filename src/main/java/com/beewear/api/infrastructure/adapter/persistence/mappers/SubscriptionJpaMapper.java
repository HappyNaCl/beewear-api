package com.beewear.api.infrastructure.adapter.persistence.mappers;

import com.beewear.api.domain.entities.Subscription;
import com.beewear.api.infrastructure.adapter.persistence.models.SubscriptionJpaModel;
import com.beewear.api.infrastructure.adapter.persistence.models.SubscriptionPlanJpaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SubscriptionPlanJpaModel.class})
public interface SubscriptionJpaMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "subscriptionPlanId", source = "subscriptionPlan.id")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "subscriptionPlan", ignore = true)
    Subscription toDomain(SubscriptionJpaModel model);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "subscriptionPlan.id", source = "subscriptionPlanId")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "subscriptionPlan", ignore = true)
    SubscriptionJpaModel toJpaModel(Subscription subscription);
}
