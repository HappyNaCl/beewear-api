package com.beewear.api.application.ports.inbound.me;

import com.beewear.api.application.services.dto.MeResult;

import java.util.UUID;

public interface GetMeUseCase {
    MeResult getMe(UUID userId);
}
