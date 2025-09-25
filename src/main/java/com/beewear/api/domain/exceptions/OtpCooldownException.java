package com.beewear.api.domain.exceptions;

public class OtpCooldownException extends RuntimeException {
    public OtpCooldownException() {
        super("Please wait before requesting a new OTP");
    }
}
