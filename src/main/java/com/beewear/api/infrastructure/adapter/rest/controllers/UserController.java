package com.beewear.api.infrastructure.adapter.rest.controllers;

import com.beewear.api.application.ports.inbound.user.GetUserDetailUseCase;
import com.beewear.api.application.ports.inbound.user.UpdateUserUseCase;
import com.beewear.api.application.services.dto.UserDto;
import com.beewear.api.domain.valueobject.UserImageFile;
import com.beewear.api.infrastructure.adapter.rest.requests.UpdateUserRequest;
import com.beewear.api.infrastructure.adapter.rest.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "API to get user data and update user data")
public class UserController {

    private final GetUserDetailUseCase getUserDetailUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserDetail(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(200, getUserDetailUseCase.getUserDetail(UUID.fromString(id))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @PathVariable String id,
            @ModelAttribute UpdateUserRequest updateUserRequest,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @AuthenticationPrincipal UUID currentUserId
    ) {
        if(!id.equals(currentUserId.toString())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error(403, "Forbidden"));
        }
        UserImageFile userImageFile = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                 userImageFile = UserImageFile.newUpload(
                        imageFile.getOriginalFilename(),
                        imageFile.getBytes(),
                        currentUserId
                );
            }catch(Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.error(500, "Failed to upload image"));
            }
        }

        UserDto updatedUser = updateUserUseCase.updateUser(
                UUID.fromString(id),
                userImageFile,
                updateUserRequest.getPhoneNumber(),
                updateUserRequest.getBio(),
                updateUserRequest.getName()
        );

        return ResponseEntity.ok(ApiResponse.success(200, updatedUser));
    }
}
