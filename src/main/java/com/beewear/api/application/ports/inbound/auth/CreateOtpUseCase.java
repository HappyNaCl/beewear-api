package com.beewear.api.application.ports.inbound.auth;

public interface CreateOtpUseCase {
    void createOtp(String email);
}
