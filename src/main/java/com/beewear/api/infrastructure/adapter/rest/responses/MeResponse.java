package com.beewear.api.infrastructure.adapter.rest.responses;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class MeResponse {
    private final UUID id;
    private final String email;
    private final String username;
    private final String profilePicture;
}
