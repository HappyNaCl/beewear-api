package com.beewear.api.infrastructure.adapter.inbound.rest.controllers;

import com.beewear.api.application.ports.inbound.auth.LoginUseCase;
import com.beewear.api.application.ports.inbound.auth.RefreshTokenUseCase;
import com.beewear.api.application.ports.inbound.auth.RegisterUseCase;
import com.beewear.api.application.services.dto.AuthResult;
import com.beewear.api.application.services.dto.RefreshTokenResult;
import com.beewear.api.infrastructure.adapter.inbound.rest.mapper.RefreshTokenResultMapper;
import com.beewear.api.infrastructure.adapter.inbound.rest.requests.LoginRequest;
import com.beewear.api.infrastructure.adapter.inbound.rest.requests.RefreshTokenRequest;
import com.beewear.api.infrastructure.adapter.inbound.rest.requests.RegisterRequest;
import com.beewear.api.infrastructure.adapter.inbound.rest.responses.ApiResponse;
import com.beewear.api.infrastructure.adapter.inbound.rest.responses.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth API", description = "API for user authentication and registration")
public class AuthController {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    private final RefreshTokenResultMapper refreshTokenResultMapper;

    @PostMapping("/login")
    @Operation(summary = "User login with username and password", description = "Gives back user's data and tokens")
    public ResponseEntity<ApiResponse<AuthResult>> login(@Valid @RequestBody LoginRequest req) {
        AuthResult res = loginUseCase.login(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(ApiResponse.success(200, res));
    }

    @PostMapping("/register")
    @Operation(summary = "User registration with email, username and password", description = "Gives back user's data and tokens")
    public ResponseEntity<ApiResponse<AuthResult>> register(@Valid @RequestBody RegisterRequest req) {
        AuthResult res = registerUseCase.register(
                req.getEmail(),
                req.getUsername(),
                req.getPassword(),
                req.getConfirmPassword()
        );
        return ResponseEntity.ok(ApiResponse.success(200, res));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token using refresh token", description = "Gives back new access token and refresh token")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(@RequestBody @Valid RefreshTokenRequest req) {
        String refreshToken = req.getRefreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, "Missing refresh token"));
        }

        RefreshTokenResult result = refreshTokenUseCase.refreshAccessToken(refreshToken);

        RefreshTokenResponse response = refreshTokenResultMapper.toResponse(result);

        return ResponseEntity.ok(ApiResponse.success(200, response));
    }
}
