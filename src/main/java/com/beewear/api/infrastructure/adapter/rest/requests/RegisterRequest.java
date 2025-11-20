package com.beewear.api.infrastructure.adapter.rest.requests;

import com.beewear.api.domain.entities.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Email can not be empty")
    private String email;

    @Size(min = 8, max = 24, message = "Username can only be 8 to 24 characters")
    @NotBlank(message = "Username can not be empty")
    private String username;

    @Size(min = 8, max = 32, message = "Username can only be 8 to 24 characters")
    private String password;

    @NotBlank(message = "Confirm Password can not be empty")
    private String confirmPassword;

    @Schema(
            description = "can only be MALE or FEMALE (case sensitive)",
            example = "MALE"
    )
    private Gender gender;

    @NotBlank(message = "OTP is required")
    private String otp;
}