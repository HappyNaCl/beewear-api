package com.beewear.api.domain.events;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ProductCreatedEvent {
    private final UUID productId;
}
