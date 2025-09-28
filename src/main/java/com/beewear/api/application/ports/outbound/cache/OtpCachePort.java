package com.beewear.api.application.ports.outbound.cache;

public interface OtpCachePort {
    void storeOtp(String email, String otp);
    String getOtp(String email);
    void deactivateOtp(String email);
    int getOtpTtl(String email);
}
