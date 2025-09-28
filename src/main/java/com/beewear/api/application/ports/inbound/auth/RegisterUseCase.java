package com.beewear.api.application.ports.inbound.auth;

import com.beewear.api.application.services.dto.AuthResult;

public interface RegisterUseCase {
    AuthResult register(String email, String username, String rawPassword, String confirmPassword, String otpSessionId);
}
