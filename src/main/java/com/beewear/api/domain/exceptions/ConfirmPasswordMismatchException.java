package com.beewear.api.domain.exceptions;

public class ConfirmPasswordMismatchException extends RuntimeException {
    public ConfirmPasswordMismatchException() {
        super("Password and Confirm Password must be the same");
    }
}
