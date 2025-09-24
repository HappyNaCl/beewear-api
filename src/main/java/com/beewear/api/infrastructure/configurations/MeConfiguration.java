package com.beewear.api.infrastructure.configurations;

import com.beewear.api.application.ports.outbound.persistence.UserRepositoryPort;
import com.beewear.api.application.services.MeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeConfiguration {

    @Bean
    public MeService meService(UserRepositoryPort userRepositoryPort) {
        return new MeService(userRepositoryPort);
    }

}
