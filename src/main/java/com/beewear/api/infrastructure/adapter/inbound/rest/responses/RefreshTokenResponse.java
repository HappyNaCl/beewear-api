package com.beewear.api.infrastructure.adapter.inbound.rest.responses;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RefreshTokenResponse {
    private final String refreshToken;
    private final String accessToken;
}
