package com.beewear.api.infrastructure.adapter.cache;

import com.beewear.api.application.ports.outbound.cache.ProductCachePort;
import com.beewear.api.domain.entities.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCacheAdapter implements ProductCachePort {

    private final String CACHE_KEY = "products:";
    private final RedisTemplate<String, Product> redisTemplate;

    @Override
    public void addProduct(Product product) {
        redisTemplate.opsForValue().set(CACHE_KEY + product.getId(), product, Duration.ofMinutes(10));
    }

    @Override
    public void removeProduct(UUID productId) {

    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public Product getProduct(UUID productId) {
        return null;
    }

    @Override
    public Map<UUID, Product> getProducts(Set<UUID> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return Map.of();
        }

        List<String> keys = productIds.stream()
                .map(id -> CACHE_KEY + id.toString())
                .toList();

        List<Product> values = redisTemplate.opsForValue().multiGet(keys);

        if (values == null || values.isEmpty() || values.stream().allMatch(Objects::isNull)) {
            return Map.of();
        }

        Map<UUID, Product> resultMap = new HashMap<>();
        Iterator<UUID> idIterator = productIds.iterator();
        Iterator<Product> valueIterator = values.iterator();

        while (idIterator.hasNext() && valueIterator.hasNext()) {
            UUID id = idIterator.next();

            Product product = valueIterator.next();
            resultMap.put(id, product);
        }

        return resultMap;
    }

}
