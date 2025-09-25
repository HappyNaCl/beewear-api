package com.beewear.api.application.ports.outbound.queue;

public interface EmailQueuePort {
    void sendOtpEmail(String to, String otp);
}

