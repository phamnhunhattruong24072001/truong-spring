package com.truong_java.spring.service.user;

import com.truong_java.spring.dto.LoginDto;
import com.truong_java.spring.dto.UserDto;
import com.truong_java.spring.entity.UserEntity;
import com.truong_java.spring.repository.UserRepository;
import com.truong_java.spring.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SuccessResponse register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            return new SuccessResponse("Email đã tồn tại", 400);
        }

        UserEntity user = new UserEntity();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setUsername(userDto.getUsername());

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return new SuccessResponse("Đăng ký thành công", 200);
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return null;
    }

    @Override
    public List<UserDto> findAllUsers() {
        return List.of();
    }

    @Override
    public SuccessResponse login(LoginDto loginDto) {
        String msg = "Tên đăng nhập hoặc mật khẩu không khớp";
        int status = 400;

        UserEntity user = userRepository.findByUsername(loginDto.getUsername());

        if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            msg = "Đăng nhập thành công";
            status = 200;
        }

        return new SuccessResponse(msg, status);
    }
}
