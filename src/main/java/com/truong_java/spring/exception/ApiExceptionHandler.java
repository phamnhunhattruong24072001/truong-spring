package com.truong_java.spring.exception;

import com.truong_java.spring.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleIndexOutOfBounds(IndexOutOfBoundsException ex, WebRequest request) {
        return ApiResponse.error(10100, "Đối tượng không tồn tại");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return ApiResponse.error(10101, "Tham số không hợp lệ");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleAll(Exception ex, WebRequest request) {
        return ApiResponse.error(10000, "Lỗi không xác định: " + ex.getMessage());
    }
}
