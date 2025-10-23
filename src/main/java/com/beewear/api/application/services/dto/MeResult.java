package com.beewear.api.application.services.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class MeResult {
    private final UUID id;
    private final String email;
    private final String username;
    private final String profilePicture;
    private final String subscriptionPlanName;
}
