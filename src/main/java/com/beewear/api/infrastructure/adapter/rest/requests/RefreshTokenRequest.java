package com.beewear.api.infrastructure.adapter.rest.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {
    @NotBlank(message = "Refresh Token can not be empty")
    private String refreshToken;
}
