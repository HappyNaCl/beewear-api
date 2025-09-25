package com.beewear.api.infrastructure.configurations;

import com.beewear.api.application.ports.outbound.queue.EmailQueuePort;
import com.beewear.api.application.services.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfiguration {

    @Bean
    public EmailService emailService(EmailQueuePort emailQueuePort) {
        return new EmailService(emailQueuePort);
    }

}
