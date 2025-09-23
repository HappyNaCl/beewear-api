package com.beewear.api.domain.exceptions;

public class AlphanumericalPasswordException extends RuntimeException {
    public AlphanumericalPasswordException() {
        super("Password must consists of one number and one letter");
    }
}
