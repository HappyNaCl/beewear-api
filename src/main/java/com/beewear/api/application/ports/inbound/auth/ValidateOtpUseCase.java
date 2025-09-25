package com.beewear.api.application.ports.inbound.auth;

public interface ValidateOtpUseCase {
    void validateOtp(String email, String otp);
}
