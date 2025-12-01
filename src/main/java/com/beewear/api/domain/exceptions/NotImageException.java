package com.beewear.api.domain.exceptions;

public class NotImageException extends RuntimeException {
    public NotImageException() {
        super("File is not a valid image");
    }
}
