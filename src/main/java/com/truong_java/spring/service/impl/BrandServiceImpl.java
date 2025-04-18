package com.truong_java.spring.service.impl;

import com.truong_java.spring.dto.BrandDto;
import com.truong_java.spring.entity.BrandEntity;
import com.truong_java.spring.repository.BrandRepository;
import com.truong_java.spring.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<BrandDto> getAllBrands() {
        List<BrandEntity> brands = brandRepository.findAll();
        return brands.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BrandEntity createBrand(BrandDto brandDto) {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName(brandDto.getName());

        return brandRepository.save(brandEntity);
    }

    @Override
    public Optional<BrandDto> getBrandById(Long id) {
        Optional<BrandEntity> brandEntity = brandRepository.findById(id);

        if (brandEntity.isPresent()) {
            BrandDto brandDto = new BrandDto();
            brandDto.setId(brandEntity.get().getId());
            brandDto.setName(brandEntity.get().getName());
            return Optional.of(brandDto);
        }

        return Optional.empty();
    }

    @Override
    public BrandEntity updateBrand(BrandDto brandDto, Long id) {
        Optional<BrandEntity> brandEntity = brandRepository.findById(id);
        if (brandEntity.isPresent()) {
            BrandEntity brandEntityUpdate = new BrandEntity();
            brandEntityUpdate.setId(id);
            brandEntityUpdate.setName(brandDto.getName());
            return brandRepository.save(brandEntityUpdate);
        }
        return null;
    }

    @Override
    public BrandEntity getBrandEntityById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand không tồn tại"));
    }

    @Override
    public void deleteBrand(Long id) {
        Optional<BrandEntity> brandEntity = brandRepository.findById(id);
        brandEntity.ifPresent(entity -> brandRepository.delete(entity));
    }

    private BrandDto mapToDto(BrandEntity brand) {
        BrandDto dto = new BrandDto();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        return dto;
    }
}
