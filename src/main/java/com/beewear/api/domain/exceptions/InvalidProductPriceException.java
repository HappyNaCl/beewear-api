package com.beewear.api.domain.exceptions;

public class InvalidProductPriceException extends RuntimeException {
    public InvalidProductPriceException() {
        super("Price can only be between 0 and 999,999,999");
    }
}
