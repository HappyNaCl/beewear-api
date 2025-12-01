package com.beewear.api.infrastructure.adapter.rest.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String phoneNumber;
    private String bio;
    private String name;
}
