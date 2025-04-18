package com.truong_java.spring.controller;

import com.truong_java.spring.dto.BrandDto;
import com.truong_java.spring.entity.BrandEntity;
import com.truong_java.spring.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/brand")
public class BrandController extends BaseController {
    private BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<BrandDto> brands = brandService.getAllBrands();
        return ok(brands);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody BrandDto brandDto) {
        BrandEntity created = brandService.createBrand(brandDto);
        if (created == null) {
            return error(HttpStatus.BAD_REQUEST, "Tạo brand thất bại");
        }
        return ok("Tạo brand thành công", created);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        Optional<BrandDto> brand = brandService.getBrandById(id);
        if (brand.isPresent()) {
            return ok("Lấy brand thành công", brand.get());
        } else {
            return error(HttpStatus.NOT_FOUND, "Brand không tồn tại");
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody BrandDto brandDto, @PathVariable("id") Long id) {
        BrandEntity updated = brandService.updateBrand(brandDto, id);
        if (updated == null) {
            return error(HttpStatus.BAD_REQUEST, "Cập nhật brand thất bại");
        }
        return ok("Cập nhật brand thành công", updated);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        brandService.deleteBrand(id);
        return ok("Xoá brand thành công", null);
    }
}
