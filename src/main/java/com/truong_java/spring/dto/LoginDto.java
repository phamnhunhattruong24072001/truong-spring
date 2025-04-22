package com.truong_java.spring.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotEmpty(message = "Tên đăng nhập không được để trống")
    private String username;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 32, message = "Mật khẩu phải từ 6 đến 32 ký tự")
    private String password;
}
