package com.beewear.api.domain.valueobject;

import java.util.UUID;

public record UserImageFile(String fileName, byte[] data, UUID userId) {
    public UserImageFile {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Image data cannot be empty");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }

    public static UserImageFile newUpload(String originalFileName, byte[] data, UUID userId) {
        return new UserImageFile(originalFileName, data, userId);
    }

    private static String generateUniqueFileName(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            return "profile";
        }

        int lastDotIndex = originalFileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < originalFileName.length() - 1) {
            String extension = originalFileName.substring(lastDotIndex);
            return "profile" + extension;
        }

        return "profile";
    }
}
