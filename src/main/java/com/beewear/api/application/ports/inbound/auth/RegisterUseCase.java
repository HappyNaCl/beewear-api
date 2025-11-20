package com.beewear.api.application.ports.inbound.auth;

import com.beewear.api.application.services.dto.AuthDto;
import com.beewear.api.domain.entities.enums.Gender;

public interface RegisterUseCase {
    AuthDto register(String email, String username, String rawPassword, String confirmPassword, String otp, Gender gender);
}
