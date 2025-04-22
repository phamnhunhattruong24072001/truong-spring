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
public class BrandDto {
    private long id;

    @NotEmpty(message = "Tên thương hiệu không được để trống")
    @Size(min = 2, max = 100, message = "Tên thương hiệu phải từ 2 đến 100 ký tự")
    private String name;
}
