package com.beewear.api.infrastructure.adapter.inbound.rest.mapper;

import com.beewear.api.application.services.dto.RefreshTokenResult;
import com.beewear.api.infrastructure.adapter.inbound.rest.responses.RefreshTokenResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenResultMapper {
    RefreshTokenResponse toResponse(RefreshTokenResult result);
}
