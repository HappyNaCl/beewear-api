package com.beewear.api.infrastructure.configurations;

import com.beewear.api.application.ports.outbound.persistence.UserRepositoryPort;
import com.beewear.api.application.ports.outbound.security.PasswordHasherPort;
import com.beewear.api.application.ports.outbound.security.TokenProviderPort;
import com.beewear.api.application.ports.outbound.security.TokenValidatorPort;
import com.beewear.api.application.services.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfiguration {

    @Bean
    public AuthService authService(UserRepositoryPort userRepository,
                                   PasswordHasherPort passwordHasher,
                                   TokenProviderPort tokenProvider,
                                   TokenValidatorPort tokenValidator) {
        return new AuthService(userRepository, passwordHasher, tokenProvider, tokenValidator);
    }
}
