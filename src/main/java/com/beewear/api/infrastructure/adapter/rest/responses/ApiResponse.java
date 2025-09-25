package com.beewear.api.infrastructure.adapter.rest.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int statusCode;
    private T data;
    private String error;

    public static <T> ApiResponse<T> success(int statusCode, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatusCode(statusCode);
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> statusOnly(int statusCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatusCode(statusCode);
        return response;
    }

    public static <T> ApiResponse<T> error(int statusCode, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatusCode(statusCode);
        response.setError(message);
        return response;
    }
}
