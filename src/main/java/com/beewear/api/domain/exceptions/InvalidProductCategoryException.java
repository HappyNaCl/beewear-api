package com.beewear.api.domain.exceptions;

public class InvalidProductCategoryException extends RuntimeException {
    public InvalidProductCategoryException() {
        super("Invalid product category");
    }
}
