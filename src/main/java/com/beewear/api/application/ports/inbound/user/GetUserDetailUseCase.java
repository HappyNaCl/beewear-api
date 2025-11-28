package com.beewear.api.application.ports.inbound.user;

import com.beewear.api.application.services.dto.UserDto;

import java.util.UUID;

public interface GetUserDetailUseCase {
    UserDto getUserDetail(UUID userId);
}
