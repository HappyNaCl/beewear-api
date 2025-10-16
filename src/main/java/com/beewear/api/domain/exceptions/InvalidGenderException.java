package com.beewear.api.domain.exceptions;

public class InvalidGenderException extends RuntimeException {
    public InvalidGenderException() {
        super("Invalid gender, can only be MALE or FEMALE");
    }
}
