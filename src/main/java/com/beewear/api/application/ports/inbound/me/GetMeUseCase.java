package com.beewear.api.application.ports.inbound.me;

import com.beewear.api.application.services.dto.MeDto;

import java.util.UUID;

public interface GetMeUseCase {
    MeDto getMe(UUID userId);
}
