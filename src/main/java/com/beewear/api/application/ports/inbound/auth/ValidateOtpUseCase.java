package com.beewear.api.application.ports.inbound.auth;

public interface ValidateOtpUseCase {

    // If validation success, returns SessionId to be used in register
    String validateOtp(String email, String otp);
}
