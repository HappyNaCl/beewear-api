package com.beewear.api.application.services.dto;

import com.beewear.api.domain.entities.User;
import lombok.Data;

import java.util.UUID;

@Data
public class AuthDto {
    private final UUID userId;
    private final String email;
    private final String username;
    private final String profilePicture;
    private final String accessToken;
    private final String refreshToken;

    public AuthDto(User user, String accessToken, String refreshToken) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.profilePicture = user.getProfilePicture();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
