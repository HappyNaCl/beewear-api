package com.beewear.api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionPlan {
    private UUID id;
    private String name;
    private String planImageUrl;
    private String description;
    private Double pricePerMonth;
}
