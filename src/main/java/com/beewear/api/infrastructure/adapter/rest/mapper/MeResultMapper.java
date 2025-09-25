package com.beewear.api.infrastructure.adapter.rest.mapper;

import com.beewear.api.application.services.dto.MeResult;
import com.beewear.api.infrastructure.adapter.rest.responses.MeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeResultMapper {
    MeResponse toResponse(MeResult result);
}
