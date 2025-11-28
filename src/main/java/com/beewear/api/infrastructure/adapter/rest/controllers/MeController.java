package com.beewear.api.infrastructure.adapter.rest.controllers;

import com.beewear.api.application.ports.inbound.me.GetMeUseCase;
import com.beewear.api.application.services.dto.MeDto;
import com.beewear.api.infrastructure.adapter.rest.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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


    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get user's data", description = "Get user's data from access token")
    public ResponseEntity<ApiResponse<MeDto>> me(@AuthenticationPrincipal UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(200, getMeUseCase.getMe(userId)));
    }
}
