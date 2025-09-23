package com.beewear.api.domain.exceptions;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super("User with this email already exists");
    }
}
