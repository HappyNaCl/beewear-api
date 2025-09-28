package com.beewear.api.infrastructure.adapter.rest.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpSessionResponse {
    private String otpSessionId;
}
