package com.beewear.api.infrastructure.adapter.cache;

import com.beewear.api.application.ports.outbound.cache.ProductDetailCachePort;
import com.beewear.api.domain.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductDetailCacheAdapter implements ProductDetailCachePort {

    private final String CACHE_KEY = "product:detail:";
    private final RedisTemplate<String, Product> redisTemplate;

    private ValueOperations<String, Product> valueOps() {
        return redisTemplate.opsForValue();
    }

    @Override
    public void invalidate(UUID id) {
        redisTemplate.delete(CACHE_KEY + id.toString());
    }

    @Override
    public Optional<Product> getProductDetail(UUID id) {
        Product product = valueOps().get(CACHE_KEY + id.toString());
        if (product != null) {
            return Optional.of(product);
        }
        return Optional.empty();
    }

    @Override
    public void setProductDetail(Product product) {
        valueOps().set(CACHE_KEY + product.getId().toString(), product, Duration.ofMinutes(5));
    }
}
