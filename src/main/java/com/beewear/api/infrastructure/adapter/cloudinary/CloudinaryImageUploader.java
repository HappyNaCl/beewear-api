package com.beewear.api.infrastructure.adapter.cloudinary;

import com.beewear.api.application.ports.outbound.s3.ImageUploaderPort;
import com.beewear.api.domain.valueobject.UploadedImage;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CloudinaryImageUploader implements ImageUploaderPort {
    private Cloudinary cloudinary;

    public CloudinaryImageUploader(CloudinaryProperties properties) {
        this.cloudinary = new Cloudinary(properties.getCloudinaryUrl());
    }

    @Override
    public UploadedImage uploadImage(String fileName, byte[] data) {
        log.info("Uploading {} to Cloudinary", fileName);
        try {
            String publicId = fileName.contains(".")
                    ? fileName.substring(0, fileName.lastIndexOf('.'))
                    : fileName;

            var uploadResult = cloudinary.uploader().upload(data,
                    ObjectUtils.asMap(
                            "public_id", publicId,
                            "overwrite", true,
                            "resource_type", "image"
                    )
            );

            return new UploadedImage(
                    (String) uploadResult.get("secure_url"),
                    (String) uploadResult.get("public_id")
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image to Cloudinary", e);
        }
    }

}
