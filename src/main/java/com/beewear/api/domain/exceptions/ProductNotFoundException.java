package com.beewear.api.domain.exceptions;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(UUID uuid) {
        super("Product with ID " + uuid + " not found.");
    }
}
