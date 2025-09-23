package com.beewear.api.application.ports.outbound.security;

import java.util.UUID;

public interface TokenValidatorPort {
    boolean validateAccessToken(String accessToken);
    boolean validateRefreshToken(String refreshToken);

    UUID getSubject(String token);
}
