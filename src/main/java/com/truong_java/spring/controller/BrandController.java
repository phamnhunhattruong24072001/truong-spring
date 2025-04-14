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
public class BrandController {
    private BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<BrandDto>> list() {
        List<BrandDto> brands = brandService.getAllBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody BrandDto brandDto) {
        BrandEntity created = brandService.createBrand(brandDto);
        if (created == null) {
            return new ResponseEntity<>("Create error", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @GetMapping(path = "/show/{id}")
    public ResponseEntity<Optional<BrandDto>> show(@PathVariable("id") Long id) {
        Optional<BrandDto> brand = brandService.getBrandById(id);
        return new ResponseEntity<>(brand, HttpStatus.OK);
    }

    @PostMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody BrandDto brandDto, @PathVariable("id") Long id) {
        BrandEntity updated = brandService.updateBrand(brandDto, id);
        if (updated == null) {
            return new ResponseEntity<>("Updated error", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        brandService.deleteBrand(id);
        return new ResponseEntity<>("Deleted Brand Success", HttpStatus.OK);
    }
}
