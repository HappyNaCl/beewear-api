package com.beewear.api.domain.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Unauthorized");
    }
}
