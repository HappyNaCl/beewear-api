package com.beewear.api.infrastructure.adapter.rest.mapper;

import com.beewear.api.application.services.dto.RefreshTokenDto;
import com.beewear.api.infrastructure.adapter.rest.responses.RefreshTokenResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenDtoMapper {
    RefreshTokenResponse toResponse(RefreshTokenDto result);
}
