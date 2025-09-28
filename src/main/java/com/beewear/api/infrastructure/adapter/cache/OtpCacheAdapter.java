package com.beewear.api.infrastructure.adapter.cache;

import com.beewear.api.application.ports.outbound.cache.OtpCachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OtpCacheAdapter implements OtpCachePort {

    private final RedisTemplate<String, String> redisTemplate;

    private String getRedisKey(String email) {
        return "email-otp:" + email;
    }

    @Override
    public void storeOtp(String email, String otp) {
        redisTemplate.opsForValue().set(getRedisKey(email), otp, Duration.ofMinutes(5));
    }

    @Override
    public String getOtp(String email) {
        return redisTemplate.opsForValue().get(getRedisKey(email));
    }

    @Override
    public void deactivateOtp(String email) {
        redisTemplate.delete(getRedisKey(email));
    }

    @Override
    public int getOtpTtl(String email) {
        Long ttl = redisTemplate.getExpire(getRedisKey(email));
        return ttl.intValue();
    }
}
