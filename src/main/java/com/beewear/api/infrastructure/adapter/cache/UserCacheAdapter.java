package com.beewear.api.infrastructure.adapter.cache;

import com.beewear.api.application.ports.outbound.cache.UserCachePort;
import com.beewear.api.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class UserCacheAdapter implements UserCachePort {

    private final String CACHE_KEY = "user:";
    private final RedisTemplate<String, User> redisTemplate;

    @Override
    public void invalidate(String userId) {
        redisTemplate.delete(CACHE_KEY + userId);
    }

    @Override
    public void setUser(User user) {
        redisTemplate.opsForValue().set(CACHE_KEY + user.getId(), user, Duration.ofMinutes(5));
    }

    @Override
    public User getUser(String userId) {
        return redisTemplate.opsForValue().get(CACHE_KEY + userId);
    }
}
