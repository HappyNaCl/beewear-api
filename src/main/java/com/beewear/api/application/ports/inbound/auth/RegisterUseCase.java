package com.beewear.api.application.ports.inbound.auth;

import com.beewear.api.application.services.dto.AuthResult;
import com.beewear.api.domain.entities.enums.Gender;

import java.util.UUID;

public interface RegisterUseCase {
    AuthResult register(String email, String username, String rawPassword, String confirmPassword, String otp, Gender gender);
}
