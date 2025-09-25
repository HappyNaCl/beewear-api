package com.beewear.api.infrastructure.adapter.queue.consumer;

import com.beewear.api.application.ports.outbound.mail.MailerPort;
import com.beewear.api.infrastructure.adapter.queue.dto.SendOtpMessageDto;
import com.beewear.api.infrastructure.configurations.RabbitConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitEmailQueueConsumer {

    private final MailerPort mailerPort;

    @RabbitListener(queues = RabbitConfiguration.OTP_QUEUE)
    public void consumeOtpEmailQueue(SendOtpMessageDto dto) {
        mailerPort.sendEmail(
                dto.getTo(),
                "Your OTP Code",
                "otp-email",
                dto.toMap());
    }

}
