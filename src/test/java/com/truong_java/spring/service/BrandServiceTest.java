package com.truong_java.spring.service;

import com.truong_java.spring.dto.BrandDto;
import com.truong_java.spring.entity.BrandEntity;
import com.truong_java.spring.repository.BrandRepository;
import com.truong_java.spring.service.impl.BrandServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {
    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandServiceImpl brandService;

    @Test
    public void getAllBrands_ShouldReturnListOfBrandDto() {
        BrandEntity brand1 = BrandEntity.builder().id(1L).name("VinFast").build();
        BrandEntity brand2 = BrandEntity.builder().id(2L).name("Toyota").build();

        List<BrandEntity> mockEntities = List.of(brand1, brand2);
        Mockito.when(brandRepository.findAll()).thenReturn(mockEntities);

        // Act
        List<BrandDto> result = brandService.getAllBrands();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("VinFast", result.get(0).getName());
        Assertions.assertEquals("Toyota", result.get(1).getName());
    }

    @Test
    void createBrand_ShouldReturnSavedBrandEntity() {
        // Arrange
        BrandDto brandDto = new BrandDto();
        brandDto.setName("Vin");

        BrandEntity mockSavedEntity = new BrandEntity();
        mockSavedEntity.setId(1L);
        mockSavedEntity.setName("Vin");

        Mockito.when(brandRepository.save(Mockito.any(BrandEntity.class)))
                .thenReturn(mockSavedEntity);

        // Act
        BrandEntity result = brandService.createBrand(brandDto);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Vin", result.getName());

        Mockito.verify(brandRepository, Mockito.times(1))
                .save(Mockito.any(BrandEntity.class));
    }

    @Test
    void getBrandById_WhenFound_ShouldReturnBrandDto() {
        // Arrange
        Long brandId = 1L;
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brandId);
        brandEntity.setName("VinFast");

        Mockito.when(brandRepository.findById(brandId))
                .thenReturn(Optional.of(brandEntity));

        // Act
        Optional<BrandDto> result = brandService.getBrandById(brandId);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(brandId, result.get().getId());
        Assertions.assertEquals("VinFast", result.get().getName());

        Mockito.verify(brandRepository, Mockito.times(1)).findById(brandId);
    }

    @Test
    void getBrandById_WhenNotFound_ShouldReturnEmpty() {
        // Arrange
        Long brandId = 1L;
        Mockito.when(brandRepository.findById(brandId))
                .thenReturn(Optional.empty());

        // Act
        Optional<BrandDto> result = brandService.getBrandById(brandId);

        // Assert
        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(brandRepository, Mockito.times(1)).findById(brandId);
    }

    @Test
    void updateBrand_WhenBrandExists_ShouldUpdateAndReturnEntity() {
        // Arrange
        Long brandId = 1L;
        BrandDto brandDto = new BrandDto();
        brandDto.setName("UpdatedBrand");

        BrandEntity existingBrand = new BrandEntity();
        existingBrand.setId(brandId);
        existingBrand.setName("OldBrand");

        BrandEntity updatedBrand = new BrandEntity();
        updatedBrand.setId(brandId);
        updatedBrand.setName("UpdatedBrand");

        Mockito.when(brandRepository.findById(brandId)).thenReturn(Optional.of(existingBrand));
        Mockito.when(brandRepository.save(Mockito.any(BrandEntity.class))).thenReturn(updatedBrand);

        // Act
        BrandEntity result = brandService.updateBrand(brandDto, brandId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(brandId, result.getId());
        Assertions.assertEquals("UpdatedBrand", result.getName());

        Mockito.verify(brandRepository, Mockito.times(1)).findById(brandId);
        Mockito.verify(brandRepository, Mockito.times(1)).save(Mockito.any(BrandEntity.class));
    }

    @Test
    void updateBrand_WhenBrandNotExists_ShouldReturnNull() {
        // Arrange
        Long brandId = 2L;
        BrandDto brandDto = new BrandDto();
        brandDto.setName("NonExisting");

        Mockito.when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        // Act
        BrandEntity result = brandService.updateBrand(brandDto, brandId);

        // Assert
        Assertions.assertNull(result);
        Mockito.verify(brandRepository, Mockito.times(1)).findById(brandId);
        Mockito.verify(brandRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deleteBrand_WhenBrandExists_ShouldCallDelete() {
        // Arrange
        Long brandId = 1L;
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brandId);
        brandEntity.setName("TestBrand");

        Mockito.when(brandRepository.findById(brandId)).thenReturn(Optional.of(brandEntity));

        // Act
        brandService.deleteBrand(brandId);

        // Assert
        Mockito.verify(brandRepository, Mockito.times(1)).findById(brandId);
        Mockito.verify(brandRepository, Mockito.times(1)).delete(brandEntity);
    }

    @Test
    void deleteBrand_WhenBrandNotExists_ShouldNotCallDelete() {
        // Arrange
        Long brandId = 2L;
        Mockito.when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        // Act
        brandService.deleteBrand(brandId);

        // Assert
        Mockito.verify(brandRepository, Mockito.times(1)).findById(brandId);
        Mockito.verify(brandRepository, Mockito.never()).delete(Mockito.any());
    }
}
