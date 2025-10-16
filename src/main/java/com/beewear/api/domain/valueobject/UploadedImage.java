package com.beewear.api.domain.valueobject;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class UploadedImage {
    private final String secureUrl;
    private final String publicId;
}
