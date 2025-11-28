package com.beewear.api.infrastructure.adapter.security;

import com.beewear.api.application.ports.outbound.security.TokenProviderPort;
import com.beewear.api.domain.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenAdapter implements TokenProviderPort {

    private final JwtProperties jwtProperties;

    @Override
    public String createAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuer(jwtProperties.getIssuer())
                .setAudience(jwtProperties.getAudience())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenTtl()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getAccessTokenSecret().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String createRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuer(jwtProperties.getIssuer())
                .setAudience(jwtProperties.getAudience())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenTtl()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getRefreshTokenSecret().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }
}
