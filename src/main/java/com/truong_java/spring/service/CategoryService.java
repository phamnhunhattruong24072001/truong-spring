package com.truong_java.spring.service;

import com.truong_java.spring.dto.CategoryDto;
import com.truong_java.spring.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    CategoryEntity createCategory(CategoryDto categoryDto);
    Optional<CategoryDto> getCategoryById(Long id);
    CategoryEntity updateCategory(CategoryDto categoryDto, Long id);
    CategoryEntity getCategoryEntityById(Long id);
    void deleteCategory(Long id);
}
