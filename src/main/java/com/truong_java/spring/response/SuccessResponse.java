package com.truong_java.spring.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse {
    String message;
    Integer statusCode;

    public SuccessResponse(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
