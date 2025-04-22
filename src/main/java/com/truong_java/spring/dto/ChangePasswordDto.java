package com.truong_java.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    @NotEmpty(message = "Mật khẩu cũ không được để trống")
    private String oldPassword;

    @NotEmpty(message = "Mật khẩu mới không được để trống")
    @Size(min = 6, max = 32, message = "Mật khẩu mới phải từ 6 đến 32 ký tự")
    private String newPassword;

    @NotEmpty(message = "Xác nhận mật khẩu không được để trống")
    private String confirmPassword;

    @NotEmpty(message = "Tên người dùng không được để trống")
    private String username;
}
