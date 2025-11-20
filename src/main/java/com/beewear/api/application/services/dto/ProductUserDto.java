package com.beewear.api.application.services.dto;

import com.beewear.api.domain.entities.User;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductUserDto {
    private UUID id;
    private String username;
    private String profilePicture;

    public static ProductUserDto fromUser(User user) {
        ProductUserDto dto = new ProductUserDto();
        dto.id = user.getId();
        dto.username = user.getUsername();
        dto.profilePicture = user.getProfilePicture();
        return dto;
    }
}
