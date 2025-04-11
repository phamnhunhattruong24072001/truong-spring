package com.truong_java.spring.controller;

import com.truong_java.spring.dto.AuthResponseDto;
import com.truong_java.spring.dto.LoginDto;
import com.truong_java.spring.dto.UserDto;
import com.truong_java.spring.response.SuccessResponse;
import com.truong_java.spring.security.JWTGenerator;
import com.truong_java.spring.service.user.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private UserServiceInterface userService;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(UserServiceInterface userService, JWTGenerator jwtGenerator, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        SuccessResponse registerResponse = userService.register(userDto);
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);

        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(new AuthResponseDto("Đăng nhập thất bại"), HttpStatus.UNAUTHORIZED);
        }
    }
}
