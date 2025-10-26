package com.beewear.api.infrastructure.adapter.rest.controllers;

import com.beewear.api.application.ports.inbound.auth.*;
import com.beewear.api.application.services.dto.AuthResult;
import com.beewear.api.application.services.dto.RefreshTokenResult;
import com.beewear.api.infrastructure.adapter.rest.mapper.RefreshTokenResultMapper;
import com.beewear.api.infrastructure.adapter.rest.requests.*;
import com.beewear.api.infrastructure.adapter.rest.responses.ApiResponse;
import com.beewear.api.infrastructure.adapter.rest.responses.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth API", description = "API for user authentication and registration")
public class AuthController {

    private final CreateOtpUseCase createOtpUseCase;
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
        if(req.getGender() == null){
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(400, "Gender is required"));
        }

        AuthResult res = registerUseCase.register(
                req.getEmail(),
                req.getUsername(),
                req.getPassword(),
                req.getConfirmPassword(),
                req.getOtp(),
                req.getGender()
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

    @PostMapping("/otp/create")
    public ResponseEntity<ApiResponse<Void>> createOtp(@Valid @RequestBody CreateOtpRequest req) {
        createOtpUseCase.createOtp(req.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(null);
    }
}
