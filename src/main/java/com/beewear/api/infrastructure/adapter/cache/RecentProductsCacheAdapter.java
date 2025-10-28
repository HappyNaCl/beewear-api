package com.beewear.api.infrastructure.adapter.cache;

import com.beewear.api.application.ports.outbound.cache.RecentProductsCachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RecentProductsCacheAdapter implements RecentProductsCachePort {

    private final String CACHE_KEY = "recent_products";
    private final RedisTemplate<String, String> redisTemplate;

    private ZSetOperations<String, String> zSetOps() {
        return redisTemplate.opsForZSet();
    }

    @Override
    public void addProduct(String productId, Long createdAt) {
        zSetOps().add(CACHE_KEY, productId, createdAt);
        zSetOps().removeRange(CACHE_KEY, 0, -1001);
        redisTemplate.expire(CACHE_KEY, Duration.ofDays(1));
    }

    @Override
    public Set<String> getRecentProducts(int limit) {
        return zSetOps().reverseRange(CACHE_KEY, 0, limit - 1);
    }

    @Override
    public Set<String> getRecentProducts(int limit, Long lastTimestamp) {
        return zSetOps().reverseRangeByScore(
                CACHE_KEY,
                Double.NEGATIVE_INFINITY,
                lastTimestamp - 1,
                0,
                limit
        );
    }
}
