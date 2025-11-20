package com.beewear.api.infrastructure.adapter.persistence.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "subscription_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlanJpaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String planImageUrl;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double pricePerMonth;
}
