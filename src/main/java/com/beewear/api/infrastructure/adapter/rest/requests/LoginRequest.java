package com.beewear.api.infrastructure.adapter.rest.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email can not be empty")
    private String email;

    @NotBlank(message = "Password can not be empty")
    private String password;

}