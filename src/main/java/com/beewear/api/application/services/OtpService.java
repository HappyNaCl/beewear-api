package com.beewear.api.application.services;

import com.beewear.api.application.ports.inbound.auth.CreateOtpUseCase;
import com.beewear.api.application.ports.outbound.cache.OtpCachePort;
import com.beewear.api.domain.exceptions.OtpCooldownException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class OtpService implements CreateOtpUseCase {
    private final EmailService emailService;
    private final OtpCachePort otpCachePort;

    private final SecureRandom random = new SecureRandom();

    @Override
    public void createOtp(String email) {
        int ttl = otpCachePort.getOtpTtl(email);

        if (ttl > (60 * 3)) {
            throw new OtpCooldownException();
        }

        String otp = generateOtp();
        otpCachePort.storeOtp(email, otp);
        emailService.sendOtpEmail(email, otp);
    }

    private String generateOtp(){
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }
}
