package com.beewear.api.infrastructure.adapter.outbound.security;

import com.beewear.api.application.ports.outbound.security.PasswordHasherPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasherProvider implements PasswordHasherPort {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hashPassword(String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean verifyPassword(String password, String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }
}
