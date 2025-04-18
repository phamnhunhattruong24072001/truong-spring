package com.truong_java.spring.controller;

import com.truong_java.spring.dto.CategoryDto;
import com.truong_java.spring.entity.CategoryEntity;
import com.truong_java.spring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController extends BaseController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ok(categories);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CategoryDto categoryDto) {
        CategoryEntity created = categoryService.createCategory(categoryDto);
        if (created == null) {
            return error(HttpStatus.BAD_REQUEST, "Tạo category thất bại");
        }
        return ok("Tạo category thành công", created);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        Optional<CategoryDto> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            return ok("Lấy category thành công", category.get());
        } else {
            return error(HttpStatus.NOT_FOUND, "Category không tồn tại");
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody CategoryDto categoryDto, @PathVariable("id") Long id) {
        CategoryEntity updated = categoryService.updateCategory(categoryDto, id);
        if (updated == null) {
            return error(HttpStatus.BAD_REQUEST, "Cập nhật category thất bại");
        }
        return ok("Cập nhật category thành công", updated);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ok("Xoá category thành công", null);
    }
}
