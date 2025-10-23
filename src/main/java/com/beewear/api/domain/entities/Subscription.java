package com.beewear.api.domain.entities;

import com.beewear.api.domain.entities.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscription {
    private UUID id;
    private UUID userId;
    private UUID subscriptionPlanId;
    private Instant startDate;
    private Instant endDate;
    private Instant nextBillingDate;
    private SubscriptionStatus subscriptionStatus;

    private User user;
    private SubscriptionPlan subscriptionPlan;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}