package com.truong_java.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private BigDecimal price;

    @NotEmpty
    private Long description;

    @NotEmpty
    private Integer stock_quantity;

    @NotEmpty
    private Long brand_id;

    @NotEmpty
    private Long category_id;
}
