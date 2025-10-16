package com.beewear.api.domain.valueobject;

import java.util.UUID;

public record ProductImageFile(String fileName, byte[] data) {

    public ProductImageFile {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Image data cannot be empty");
        }
    }

    private static String generateUniqueFileName(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            return UUID.randomUUID().toString();
        }

        int lastDotIndex = originalFileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < originalFileName.length() - 1) {
            String extension = originalFileName.substring(lastDotIndex);
            return UUID.randomUUID() + extension;
        }

        return UUID.randomUUID().toString();
    }

    public static ProductImageFile newUpload(String originalFileName, byte[] data) {
        return new ProductImageFile(generateUniqueFileName(originalFileName), data);
    }
}
