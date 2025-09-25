package com.beewear.api.infrastructure.adapter.queue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendOtpMessageDto {
    private String to;
    private String otp;

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();

        map.put("email", to);
        map.put("otp", otp);

        return map;
    }
}
