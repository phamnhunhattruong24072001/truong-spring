package com.truong_java.spring.controller;

import com.truong_java.spring.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    protected <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    protected <T> ResponseEntity<ApiResponse<T>> ok(String message, T data) {
        return ResponseEntity.ok(ApiResponse.success(message, data));
    }

    protected ResponseEntity<ApiResponse<Object>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ApiResponse.error(status.value(), message));
    }
}
