package com.beewear.api.application.services;

import com.beewear.api.application.ports.outbound.queue.EmailQueuePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final EmailQueuePort emailQueuePort;

    public void sendOtpEmail(String to, String otp) {
        emailQueuePort.sendOtpEmail(to, otp);
    }
}
