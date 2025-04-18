package com.truong_java.spring.service;

import com.truong_java.spring.dto.BrandDto;
import com.truong_java.spring.entity.BrandEntity;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<BrandDto> getAllBrands();
    BrandEntity createBrand(BrandDto brandDto);
    Optional<BrandDto> getBrandById(Long id);
    BrandEntity updateBrand(BrandDto brandDto, Long id);
    BrandEntity getBrandEntityById(Long id);
    void deleteBrand(Long id);
}
