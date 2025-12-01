package com.beewear.api.application.services;

import com.beewear.api.application.ports.inbound.user.GetUserDetailUseCase;
import com.beewear.api.application.ports.inbound.user.UpdateUserUseCase;
import com.beewear.api.application.ports.outbound.cache.UserCachePort;
import com.beewear.api.application.ports.outbound.persistence.UserRepositoryPort;
import com.beewear.api.application.ports.outbound.s3.ImageUploaderPort;
import com.beewear.api.application.services.dto.UserDto;
import com.beewear.api.domain.entities.User;
import com.beewear.api.domain.valueobject.UploadedImage;
import com.beewear.api.domain.valueobject.UserImageFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserService implements GetUserDetailUseCase, UpdateUserUseCase {

    private final UserRepositoryPort userRepository;
    private final ImageUploaderPort imageUploader;
    private final UserCachePort userCache;

    @Override
    public UserDto getUserDetail(UUID userId) {
        User user = userCache.getUser(userId.toString());
        if (user == null) {
            user = userRepository.findById(userId);
            userCache.setUser(user);
        }

        return UserDto.fromUser(user);
    }

    @Override
    public UserDto updateUser(UUID id, UserImageFile profilePicture, String phoneNumber, String bio, String displayName) {

        if(bio.length() > 300) {
            throw new IllegalArgumentException("Bio cannot be longer than 300 characters");
        }

        if(phoneNumber.length() < 10) {
            throw new IllegalArgumentException("Phone number must be more than 10 digits");
        }

        if(displayName.length() <= 6 || displayName.length() > 30) {
            throw new IllegalArgumentException("Display name must be between 6 and 30 characters");
        }

        UploadedImage uploadedProfilePicture = null;
        if(profilePicture != null) {
            uploadedProfilePicture = imageUploader.uploadImage(profilePicture.fileName(),
                    profilePicture.data(), "users/" + id);
        }

        User oldUser;
        if(uploadedProfilePicture != null) {
            oldUser = User.builder()
                    .id(id)
                    .profilePicture(uploadedProfilePicture.getSecureUrl())
                    .phoneNumber(phoneNumber)
                    .bio(bio)
                    .displayName(displayName)
                    .build();
        }else {
            oldUser = User.builder()
                    .id(id)
                    .phoneNumber(phoneNumber)
                    .bio(bio)
                    .displayName(displayName)
                    .build();
        }


        User updatedUser = userRepository.update(oldUser);
        userCache.invalidate(id.toString());
        return UserDto.fromUser(updatedUser);
    }
}
