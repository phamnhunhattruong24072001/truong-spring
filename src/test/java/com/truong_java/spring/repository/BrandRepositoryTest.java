package com.truong_java.spring.repository;

import com.truong_java.spring.entity.BrandEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class BrandRepositoryTest {
    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void BrandRepositoryTest_SaveBrand_ReturnBrandEntity() {
        BrandEntity brand = BrandEntity.builder().name("Vin").build();

        BrandEntity savedBrand = brandRepository.save(brand);

        Assertions.assertNotNull(savedBrand);
        Assertions.assertEquals(brand.getId(), savedBrand.getId());
    }

    @Test
    public void BrandRepositoryTest_FindAllBrands_ReturnAllBrands() {
        BrandEntity brand = BrandEntity.builder().name("Vin").build();
        BrandEntity brand2 = BrandEntity.builder().name("Vin 2").build();

        brandRepository.save(brand);
        brandRepository.save(brand2);

        List<BrandEntity> allBrands = brandRepository.findAll();

        Assertions.assertNotNull(allBrands);
        Assertions.assertEquals(2, allBrands.size());
    }
}
