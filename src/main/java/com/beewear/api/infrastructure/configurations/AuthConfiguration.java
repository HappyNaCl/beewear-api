package com.beewear.api.infrastructure.configurations;

import com.beewear.api.application.ports.outbound.persistence.UserRepositoryPort;
import com.beewear.api.application.ports.outbound.security.PasswordHasherPort;
import com.beewear.api.application.ports.outbound.security.TokenProviderPort;
import com.beewear.api.application.ports.outbound.security.TokenValidatorPort;
import com.beewear.api.application.services.AuthService;
import com.beewear.api.infrastructure.adapter.outbound.security.JwtProperties;
import com.beewear.api.infrastructure.adapter.outbound.security.JwtTokenProvider;
import com.beewear.api.infrastructure.adapter.outbound.security.JwtTokenValidator;
import com.beewear.api.infrastructure.adapter.outbound.security.PasswordHasherProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfiguration {

    @Bean
    public PasswordHasherPort passwordHasher() {
        return new PasswordHasherProvider();
    }

    @Bean
    public TokenProviderPort tokenProvider(JwtProperties jwtProperties) {
        return new JwtTokenProvider(jwtProperties);
    }

    @Bean
    public TokenValidatorPort tokenValidator(JwtProperties jwtProperties) {
        return new JwtTokenValidator(jwtProperties);
    }

    @Bean
    public AuthService authService(UserRepositoryPort userRepository,
                                   PasswordHasherPort passwordHasher,
                                   TokenProviderPort tokenProvider) {
        return new AuthService(userRepository, passwordHasher, tokenProvider);
    }
}
