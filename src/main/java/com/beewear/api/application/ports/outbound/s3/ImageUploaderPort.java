package com.beewear.api.application.ports.outbound.s3;

import com.beewear.api.domain.valueobject.UploadedImage;

public interface ImageUploaderPort {
    UploadedImage uploadImage(String fileName, byte[] data);
    UploadedImage uploadImage(String fileName, byte[] data, String folder);
}
