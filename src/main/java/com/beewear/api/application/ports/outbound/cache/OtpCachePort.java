package com.beewear.api.application.ports.outbound.cache;

public interface OtpCachePort {
    void storeOtp(String email, String otp);
    boolean validateOtp(String email, String otp);
    int getOtpTtl(String email);
}
