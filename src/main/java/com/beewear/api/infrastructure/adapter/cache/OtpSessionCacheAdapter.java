package com.beewear.api.infrastructure.adapter.cache;

import com.beewear.api.application.ports.outbound.cache.OtpSessionCachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OtpSessionCacheAdapter implements OtpSessionCachePort {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void storeOtpSession(String email, String otpSessionId) {
        redisTemplate.opsForValue().set(getRedisKey(email), otpSessionId, Duration.ofMinutes(10));
    }

    @Override
    public String getOtpSession(String email) {
        return redisTemplate.opsForValue().get(getRedisKey(email));
    }

    @Override
    public void deactivateOtpSession(String email) {
        redisTemplate.delete(getRedisKey(email));
    }

    private String getRedisKey(String email) {
        return String.format("otp-session:%s", email);
    }
}
