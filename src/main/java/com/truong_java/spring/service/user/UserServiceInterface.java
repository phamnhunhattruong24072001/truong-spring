package com.truong_java.spring.service.user;

import com.truong_java.spring.dto.LoginDto;
import com.truong_java.spring.dto.UserDto;
import com.truong_java.spring.entity.UserEntity;
import com.truong_java.spring.response.SuccessResponse;

import java.util.List;

public interface UserServiceInterface {
    SuccessResponse register(UserDto userDto);

    UserEntity findUserByEmail(String email);

    List<UserDto> findAllUsers();

    SuccessResponse login(LoginDto loginDto);
}
