package com.beewear.api.infrastructure.adapter.inbound.rest.controllers;

import com.beewear.api.application.ports.inbound.auth.LoginUseCase;
import com.beewear.api.application.ports.inbound.auth.RegisterUseCase;
import com.beewear.api.application.services.dto.AuthResult;
import com.beewear.api.infrastructure.adapter.inbound.rest.requests.LoginRequest;
import com.beewear.api.infrastructure.adapter.inbound.rest.requests.RegisterRequest;
import com.beewear.api.infrastructure.adapter.inbound.rest.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth API", description = "API for user authentication and registration")
public class AuthController {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;

    @PostMapping("/login")
    @Operation(summary = "User login with username and password", description = "Gives back user's data and tokens")
    public ResponseEntity<ApiResponse<AuthResult>> login(@Valid @RequestBody LoginRequest req) {
        AuthResult res = loginUseCase.login(req.getEmail(), req.getPassword());

        HttpHeaders headers = setCookie(res.getRefreshToken());

        return new ResponseEntity<>(ApiResponse.success(200, res), headers, HttpStatus.OK);
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

        HttpHeaders headers = setCookie(res.getRefreshToken());

        return new ResponseEntity<>(ApiResponse.success(200, res), headers, HttpStatus.OK);

    }

    private static HttpHeaders setCookie(String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(30 * 24 * 60 * 60)
                .sameSite("None")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return headers;
    }

}
