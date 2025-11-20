package com.beewear.api.application.ports.outbound.events;

import com.beewear.api.domain.events.ProductCreatedEvent;

public interface ProductEventPublisherPort {
    void publish(ProductCreatedEvent event);
}
