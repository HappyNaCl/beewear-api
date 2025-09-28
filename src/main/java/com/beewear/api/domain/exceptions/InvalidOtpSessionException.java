package com.beewear.api.domain.exceptions;

public class InvalidOtpSessionException extends RuntimeException {
    public InvalidOtpSessionException() {
        super("Invalid OTP Session");
    }
}
