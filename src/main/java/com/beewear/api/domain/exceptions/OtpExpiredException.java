package com.beewear.api.domain.exceptions;

public class OtpExpiredException extends RuntimeException {
    public OtpExpiredException() {
        super("OTP expired");
    }
}
