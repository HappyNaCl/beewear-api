package com.beewear.api.application.ports.outbound.security;

import com.beewear.api.domain.entities.User;

public interface TokenProviderPort {
    String createAccessToken(User user);
    String createRefreshToken(User user);

}
