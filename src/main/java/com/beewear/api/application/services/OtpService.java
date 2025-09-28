package com.beewear.api.application.services;

import com.beewear.api.application.ports.inbound.auth.CreateOtpUseCase;
import com.beewear.api.application.ports.inbound.auth.ValidateOtpUseCase;
import com.beewear.api.application.ports.outbound.cache.OtpCachePort;
import com.beewear.api.application.ports.outbound.cache.OtpSessionCachePort;
import com.beewear.api.domain.exceptions.OtpCooldownException;
import com.beewear.api.domain.exceptions.OtpExpiredException;
import com.beewear.api.domain.exceptions.OtpMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class OtpService implements CreateOtpUseCase, ValidateOtpUseCase {

    private final EmailService emailService;
    private final OtpCachePort otpCachePort;
    private final OtpSessionCachePort otpSessionCachePort;

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

    @Override
    public String validateOtp(String email, String otp) {
        String cachedOtp = otpCachePort.getOtp(email);

        if(cachedOtp == null){
            throw new OtpExpiredException();
        }

        if(!cachedOtp.equals(otp)){
            throw new OtpMismatchException();
        }

        String sessionId = generateRandomString(32);
        otpSessionCachePort.storeOtpSession(email, sessionId);
        otpCachePort.deactivateOtp(email);
        return sessionId;
    }

    private String generateOtp(){
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }

    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHANUMERIC.length());
            sb.append(ALPHANUMERIC.charAt(index));
        }
        return sb.toString();
    }

}
