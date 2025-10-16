package com.beewear.api.domain.exceptions;

public class NoImageException extends RuntimeException {
    public NoImageException() {
        super("Product listing must have at least 1 image");
    }
}
