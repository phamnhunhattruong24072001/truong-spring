package com.truong_java.spring.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
