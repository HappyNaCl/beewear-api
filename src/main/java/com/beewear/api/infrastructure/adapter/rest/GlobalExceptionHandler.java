package com.beewear.api.infrastructure.adapter.rest;

import com.beewear.api.domain.exceptions.*;
import com.beewear.api.infrastructure.adapter.rest.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidOtpSessionException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidOtpSessionException(InvalidOtpSessionException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, ex.getMessage()));
    }

    @ExceptionHandler(OtpSessionExpiredException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidOtpSessionException(OtpSessionExpiredException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, ex.getMessage()));
    }

    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidOtpSessionException(OtpExpiredException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, ex.getMessage()));
    }

    @ExceptionHandler(OtpMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidOtpSessionException(OtpMismatchException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, ex.getMessage()));
    }


    @ExceptionHandler(ConfirmPasswordMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handlePasswordMismatch(ConfirmPasswordMismatchException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Object>> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(401, ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(401, ex.getMessage()));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateEmail(DuplicateEmailException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, ex.getMessage()));
    }

    @ExceptionHandler(AlphanumericalPasswordException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateEmail(AlphanumericalPasswordException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    String msg = error.getDefaultMessage();
                    return (msg != null && !msg.isBlank())
                            ? msg
                            : String.format("Invalid value for field '%s'", error.getField());
                })
                .findFirst()
                .orElse("Validation failed");

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(400, errorMessage));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadableException(){
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, "Bad request"));
    }

    @ExceptionHandler(InvalidProductPriceException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidProductPriceException(InvalidProductPriceException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, ex.getMessage()));
    }

    @ExceptionHandler(InvalidProductCategoryException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidProductCategoryException(InvalidProductCategoryException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(400, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity
                .internalServerError()
                .body(ApiResponse.error(500, "An unexpected error occurred: " + ex.getMessage()));
    }

}
