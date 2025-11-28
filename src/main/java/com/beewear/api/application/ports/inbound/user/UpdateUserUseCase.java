package com.beewear.api.application.ports.inbound.user;

import com.beewear.api.application.services.dto.UserDto;
import com.beewear.api.domain.valueobject.UserImageFile;

import java.util.UUID;

public interface UpdateUserUseCase {
    UserDto updateUser(UUID id, UserImageFile profilePicture, String phoneNumber, String bio, String displayName);
}
