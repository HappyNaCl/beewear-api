package com.beewear.api.domain.exceptions;

public class OtpSessionExpiredException extends RuntimeException {
    public OtpSessionExpiredException() {
        super("OTP session expired");
    }
}
