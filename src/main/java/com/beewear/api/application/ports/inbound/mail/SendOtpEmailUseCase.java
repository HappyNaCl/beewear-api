package com.beewear.api.application.ports.inbound.mail;

public interface SendOtpEmailUseCase {
    void sendOtpEmail(String to, String otp);
}
