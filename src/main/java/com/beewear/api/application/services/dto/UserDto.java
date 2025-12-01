package com.beewear.api.application.services.dto;

import com.beewear.api.domain.entities.User;
import com.beewear.api.domain.entities.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private String profilePicture;
    private String phoneNumber;
    private String bio;
    private String displayName;
    private Gender gender;

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture())
                .phoneNumber(user.getPhoneNumber())
                .bio(user.getBio())
                .displayName(user.getDisplayName())
                .gender(user.getGender())
                .build();
    }
}
