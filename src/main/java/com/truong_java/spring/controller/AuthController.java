package com.truong_java.spring.controller;

import com.truong_java.spring.dto.AuthResponseDto;
import com.truong_java.spring.dto.ChangePasswordDto;
import com.truong_java.spring.dto.LoginDto;
import com.truong_java.spring.dto.UserDto;
import com.truong_java.spring.entity.UserEntity;
import com.truong_java.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        UserEntity register = userService.register(userDto);
        return ok(register);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            String token = userService.authenticateAndGenerateToken(loginDto.getUsername(), loginDto.getPassword());
            return ok("Đăng nhập thành công", new AuthResponseDto(token));
        } catch (AuthenticationException ex) {
            return error(HttpStatus.UNAUTHORIZED, "Đăng nhập thất bại");
        }
    }

    @PostMapping(path = "/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        String changePassword = userService.changePassword(changePasswordDto);
        return ok(changePassword);
    }
}
