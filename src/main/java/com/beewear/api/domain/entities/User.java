package com.beewear.api.domain.entities;

import com.beewear.api.domain.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String profilePicture;
    private Gender gender;

    private UUID regionId;

    private Instant createdAt;
    private Instant updatedAt;
}
