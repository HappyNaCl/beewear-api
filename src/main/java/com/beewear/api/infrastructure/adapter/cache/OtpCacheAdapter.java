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
    public boolean validateOtp(String email, String otp) {
        String cachedOtp = redisTemplate.opsForValue().get(getRedisKey(email));
        return otp.equals(cachedOtp);
    }

    @Override
    public int getOtpTtl(String email) {
        Long ttl = redisTemplate.getExpire(getRedisKey(email));
        return ttl.intValue();
    }
}
