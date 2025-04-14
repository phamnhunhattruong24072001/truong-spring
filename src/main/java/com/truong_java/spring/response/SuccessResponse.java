package com.truong_java.spring.response;

import lombok.Data;

@Data
public class SuccessResponse {
    String message;
    Integer statusCode;

    public SuccessResponse(String message, Integer statusCode) {
        this.message = message;
    }
}
