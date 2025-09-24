package com.beewear.api.infrastructure.adapter.inbound.rest.controllers;

import com.beewear.api.application.ports.inbound.me.GetMeUseCase;
import com.beewear.api.infrastructure.adapter.inbound.rest.mapper.MeResultMapper;
import com.beewear.api.infrastructure.adapter.inbound.rest.responses.ApiResponse;
import com.beewear.api.infrastructure.adapter.inbound.rest.responses.MeResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/me")
@Tag(name = "Me API", description = "API for current user operations")
public class MeController {

    private final GetMeUseCase getMeUseCase;

    private final MeResultMapper meResultMapper;

    @GetMapping
    private ResponseEntity<ApiResponse<MeResponse>> me(@AuthenticationPrincipal UUID userId) {
        MeResponse res = meResultMapper.toResponse(getMeUseCase.getMe(userId));
        return ResponseEntity.ok(ApiResponse.success(200, res));
    }
}
