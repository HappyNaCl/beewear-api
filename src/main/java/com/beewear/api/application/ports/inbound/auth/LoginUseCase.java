package com.beewear.api.application.ports.inbound.auth;

import com.beewear.api.application.services.dto.AuthDto;

public interface LoginUseCase {
    AuthDto login(String email, String rawPassword);
}
