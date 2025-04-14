package com.truong_java.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @NotEmpty
    private String name;

    @NotEmpty
    private BigDecimal price;

    @NotEmpty
    private Long description;

    @NotEmpty
    private Integer stock_quantity;

    @NotEmpty
    private Integer brand_id;

    @NotEmpty
    private Integer category_id;
}
