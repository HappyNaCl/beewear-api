package com.beewear.api.application.ports.inbound.auth;

import com.beewear.api.application.services.dto.AuthResult;

public interface LoginUseCase {
    AuthResult login(String email, String rawPassword);
}
