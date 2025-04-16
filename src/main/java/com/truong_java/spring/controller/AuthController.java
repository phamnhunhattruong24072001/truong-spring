package com.truong_java.spring.controller;

import com.truong_java.spring.dto.AuthResponseDto;
import com.truong_java.spring.dto.ChangePasswordDto;
import com.truong_java.spring.dto.LoginDto;
import com.truong_java.spring.dto.UserDto;
import com.truong_java.spring.entity.UserEntity;
import com.truong_java.spring.response.ApiResponse;
import com.truong_java.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        UserEntity register = userService.register(userDto);
        return new ResponseEntity<>(register, HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(@RequestBody LoginDto loginDto) {
        try {
            String token = userService.authenticateAndGenerateToken(loginDto.getUsername(), loginDto.getPassword());
            return new ResponseEntity<>(ApiResponse.success("Đăng nhập thành công", new AuthResponseDto(token)), HttpStatus.OK);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "Đăng nhập thất bại"), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        String changePassword = userService.changePassword(changePasswordDto);
        return new ResponseEntity<>(changePassword, HttpStatus.OK);
    }
}
