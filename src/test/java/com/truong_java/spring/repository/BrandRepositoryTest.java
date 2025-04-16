package com.truong_java.spring.repository;

import com.truong_java.spring.entity.BrandEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void BrandRepositoryTest_FindById_ReturnBrand() {
        BrandEntity brand = BrandEntity.builder().name("Vin").build();

        brandRepository.save(brand);

        BrandEntity brandEntity = brandRepository.findById(brand.getId()).get();

        Assertions.assertNotNull(brandEntity);
    }

    @Test
    public void BrandRepositoryTest_UpdateBrand_ReturnBrandEntity() {
        BrandEntity brand = BrandEntity.builder().name("Vin").build();

        BrandEntity savedBrand = brandRepository.save(brand);

        BrandEntity updatedBrand = BrandEntity.builder().id(savedBrand.getId()).name(savedBrand.getName()).build();

        brandRepository.save(updatedBrand);

        Assertions.assertNotNull(savedBrand);
        Assertions.assertEquals(updatedBrand.getId(), savedBrand.getId());
    }

    @Test
    public void BrandRepositoryTest_DeleteBrand_RemovesBrandFromDatabase() {
        BrandEntity brand = BrandEntity.builder().name("Vin").build();
        BrandEntity savedBrand = brandRepository.save(brand);

        brandRepository.deleteById(savedBrand.getId());

        Optional<BrandEntity> deletedBrand = brandRepository.findById(savedBrand.getId());
        Assertions.assertTrue(deletedBrand.isEmpty());
    }
}
