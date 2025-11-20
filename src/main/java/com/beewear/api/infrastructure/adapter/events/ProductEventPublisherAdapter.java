package com.beewear.api.infrastructure.adapter.events;

import com.beewear.api.application.ports.outbound.events.ProductEventPublisherPort;
import com.beewear.api.domain.events.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProductEventPublisherAdapter implements ProductEventPublisherPort {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(ProductCreatedEvent event) {
        eventPublisher.publishEvent(event);
    }
}
