package com.beewear.api.infrastructure.adapter.persistence.models;

import com.beewear.api.domain.entities.enums.SubscriptionStatus;
import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "subscriptions", indexes = {
    @Index(name = "idx_user_status", columnList = "user_id, subscriptionStatus"),
    @Index(name = "idx_next_billing_date", columnList = "nextBillingDate")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE subscriptions SET deleted_at = NOW(), subscription_status = 'INACTIVE' WHERE id = ?")
@FilterDef(
        name = "deletedSubscriptionFilter"
)
@Filter(
        name = "deletedSubscriptionFilter",
        condition = "deleted_at IS NULL"
)
public class SubscriptionJpaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_plan_id", nullable = false)
    private SubscriptionPlanJpaModel subscriptionPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaModel user;

    private Instant nextBillingDate;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

    @Column(nullable = false)
    private Instant startDate;

    private Instant endDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    @Column(name = "deleted_at")
    private Instant deletedAt;
}
