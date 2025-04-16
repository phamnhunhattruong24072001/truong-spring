package com.truong_java.spring.service;

import com.truong_java.spring.dto.ChangePasswordDto;
import com.truong_java.spring.dto.UserDto;
import com.truong_java.spring.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity register(UserDto userDto);
    List<UserDto> findAllUsers();
    String authenticateAndGenerateToken(String username, String password);
    String changePassword(ChangePasswordDto changePasswordDto);
}
