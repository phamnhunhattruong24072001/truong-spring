package com.truong_java.spring.service.impl;

import com.truong_java.spring.dto.CategoryDto;
import com.truong_java.spring.entity.CategoryEntity;
import com.truong_java.spring.repository.CategoryRepository;
import com.truong_java.spring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public List<CategoryDto> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryEntity createCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryDto.getName());

        return categoryRepository.save(categoryEntity);
    }

    @Override
    public Optional<CategoryDto> getCategoryById(Long id) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);

        if (categoryEntity.isPresent()) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(categoryEntity.get().getId());
            categoryDto.setName(categoryEntity.get().getName());
            return Optional.of(categoryDto);
        }

        return Optional.empty();
    }

    @Override
    public CategoryEntity updateCategory(CategoryDto categoryDto, Long id) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if (categoryEntity.isPresent()) {
            CategoryEntity categoryEntityUpdate = new CategoryEntity();
            categoryEntityUpdate.setId(id);
            categoryEntityUpdate.setName(categoryDto.getName());
            return categoryRepository.save(categoryEntityUpdate);
        }
        return null;
    }

    @Override
    public CategoryEntity getCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        categoryEntity.ifPresent(entity -> categoryRepository.delete(entity));
    }

    private CategoryDto mapToDto(CategoryEntity category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
}
