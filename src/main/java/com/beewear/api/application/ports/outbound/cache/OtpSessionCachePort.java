package com.beewear.api.application.ports.outbound.cache;

public interface OtpSessionCachePort {
    void storeOtpSession(String email, String otpSessionId);
    String getOtpSession(String email);
    void deactivateOtpSession(String email);
}
