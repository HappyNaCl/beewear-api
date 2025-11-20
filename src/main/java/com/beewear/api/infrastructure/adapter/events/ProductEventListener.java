package com.beewear.api.infrastructure.adapter.events;

import com.beewear.api.application.ports.outbound.cache.ProductCachePort;
import com.beewear.api.application.ports.outbound.cache.RecentProductsCachePort;
import com.beewear.api.application.ports.outbound.persistence.ProductRepositoryPort;
import com.beewear.api.domain.entities.Product;
import com.beewear.api.domain.events.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ProductEventListener {
    private final ProductCachePort productCachePort;
    private final RecentProductsCachePort recentProductsCachePort;
    private final ProductRepositoryPort productRepositoryPort;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleProductCreatedEvent(ProductCreatedEvent event) {
        Product product = productRepositoryPort.findById(event.getProductId());
        productCachePort.addProduct(product);
        recentProductsCachePort.addProduct(
                product.getId().toString(),
                product.getCreatedAt().getEpochSecond()
        );
    }

}
