package com.beewear.api.application.ports.outbound.persistence;

import com.beewear.api.domain.entities.Subscription;

import java.util.UUID;

public interface SubscriptionRepositoryPort {
    Subscription findActiveSubscriptionByUserId(UUID userId);
}
