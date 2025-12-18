package com.beewear.api.application.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatsDto {
    private long soldCount;
    private long activeCount;
}