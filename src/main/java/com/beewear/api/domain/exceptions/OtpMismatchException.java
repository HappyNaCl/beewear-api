package com.beewear.api.domain.exceptions;

public class OtpMismatchException extends RuntimeException {
    public OtpMismatchException() {
        super("The provided OTP is incorrect");
    }
}
