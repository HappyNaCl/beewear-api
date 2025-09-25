package com.beewear.api.application.services;

import com.beewear.api.application.ports.inbound.mail.SendOtpEmailUseCase;
import com.beewear.api.application.ports.outbound.queue.EmailQueuePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailService implements SendOtpEmailUseCase {

    private final EmailQueuePort emailQueuePort;

    @Override
    public void sendOtpEmail(String to, String otp) {
        emailQueuePort.sendOtpEmail(to, otp);
    }
}
