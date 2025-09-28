package com.beewear.api.infrastructure.adapter.rest.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOtpRequest {

    @NotBlank(message = "Email can not be empty")
    @Email(message = "Please use a valid email")
    private String email;
}
