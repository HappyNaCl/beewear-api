package com.beewear.api.infrastructure.adapter.outbound.security;

import com.beewear.api.application.ports.outbound.security.TokenValidatorPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
public class JwtTokenValidator implements TokenValidatorPort {

    private final JwtProperties jwtProperties;

    @Override
    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, jwtProperties.getAccessTokenSecret());
    }

    @Override
    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, jwtProperties.getRefreshTokenSecret());
    }

    @Override
    public UUID getSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getAccessTokenSecret().getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return UUID.fromString(claims.getSubject());
    }

    private boolean validateToken(String token, String secret) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();
            return expiration != null && expiration.after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
