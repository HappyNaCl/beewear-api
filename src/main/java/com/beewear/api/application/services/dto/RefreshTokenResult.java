package com.beewear.api.application.services.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class RefreshTokenResult {
    private final String refreshToken;
    private final String accessToken;
}
