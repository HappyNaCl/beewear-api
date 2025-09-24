package com.beewear.api.application.ports.inbound.auth;

import com.beewear.api.application.services.dto.RefreshTokenResult;

public interface RefreshTokenUseCase {
    RefreshTokenResult refreshAccessToken(String refreshToken);
}
