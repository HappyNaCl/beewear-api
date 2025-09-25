package com.beewear.api.infrastructure.adapter.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
    private String accessTokenSecret;
    private String refreshTokenSecret;

    private long accessTokenTtl;
    private long refreshTokenTtl;

    private String issuer;
    private String audience;
}