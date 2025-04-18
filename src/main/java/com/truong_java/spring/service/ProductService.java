package com.truong_java.spring.service;

import com.truong_java.spring.dto.ProductDto;
import com.truong_java.spring.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductEntity createProduct(ProductDto productDto);
    Optional<ProductDto> getProductById(Long id);
    ProductEntity updateProduct(ProductDto productDto, Long id);
    void deleteProduct(Long id);
}
