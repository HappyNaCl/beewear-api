package com.beewear.api.application.ports.inbound.auth;

import com.beewear.api.application.services.dto.RefreshTokenDto;

public interface RefreshTokenUseCase {
    RefreshTokenDto refreshAccessToken(String refreshToken);
}
