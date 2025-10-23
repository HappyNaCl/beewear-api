package com.beewear.api.infrastructure.adapter.persistence;

import com.beewear.api.application.ports.outbound.persistence.SubscriptionRepositoryPort;
import com.beewear.api.domain.entities.Subscription;
import com.beewear.api.infrastructure.adapter.persistence.mappers.SubscriptionJpaMapper;
import com.beewear.api.infrastructure.adapter.persistence.repositories.SpringSubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@AllArgsConstructor
@Repository
public class SubscriptionRepositoryAdapter implements SubscriptionRepositoryPort {

    private SpringSubscriptionRepository subscriptionRepository;
    private SubscriptionJpaMapper mapper;

    @Override
    public Subscription findActiveSubscriptionByUserId(UUID userId) {
        return subscriptionRepository
                .findActiveByUserId(userId)
                .map(mapper::toDomain)
                .orElse(null);
    }
}
