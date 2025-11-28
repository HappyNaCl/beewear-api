package com.beewear.api.infrastructure.adapter.rest.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private UUID id;
    private String phoneNumber;
    private String bio;
    private String name;
}
