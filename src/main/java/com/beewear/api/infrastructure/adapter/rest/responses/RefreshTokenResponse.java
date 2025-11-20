package com.beewear.api.infrastructure.adapter.rest.responses;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RefreshTokenResponse {
    private final String accessToken;
    private final String refreshToken;
}
