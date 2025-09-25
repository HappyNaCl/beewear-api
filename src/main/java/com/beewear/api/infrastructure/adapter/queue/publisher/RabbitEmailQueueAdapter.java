package com.beewear.api.infrastructure.adapter.queue.publisher;

import com.beewear.api.application.ports.outbound.queue.EmailQueuePort;
import com.beewear.api.infrastructure.adapter.queue.dto.SendOtpMessageDto;
import com.beewear.api.infrastructure.configurations.RabbitConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitEmailQueueAdapter implements EmailQueuePort {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendOtpEmail(String to, String otp) {
        rabbitTemplate.convertAndSend(
                RabbitConfiguration.OTP_QUEUE,
                new SendOtpMessageDto(to, otp)
        );
    }
}
