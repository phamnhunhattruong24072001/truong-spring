package com.truong_java.spring.service.impl;

import com.truong_java.spring.dto.ProductDto;
import com.truong_java.spring.entity.BrandEntity;
import com.truong_java.spring.entity.CategoryEntity;
import com.truong_java.spring.entity.ProductEntity;
import com.truong_java.spring.repository.ProductRepository;
import com.truong_java.spring.service.BrandService;
import com.truong_java.spring.service.CategoryService;
import com.truong_java.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, BrandService brandService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductEntity createProduct(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        mapProductDtoToEntity(productDto, productEntity, false);
        return productRepository.save(productEntity);
    }

    @Override
    public Optional<ProductDto> getProductById(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if (productEntity.isPresent()) {
            ProductDto productDto = new ProductDto();
            productDto.setId(productEntity.get().getId());
            productDto.setName(productEntity.get().getName());
            productDto.setPrice(productEntity.get().getPrice());
            productDto.setDescription(productEntity.get().getDescription());
            productDto.setStock_quantity(productEntity.get().getStock_quantity());
            return Optional.of(productDto);
        }
        return Optional.empty();
    }

    @Override
    public ProductEntity updateProduct(ProductDto productDto, Long id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if (productEntity.isPresent()) {
            ProductEntity productEntityUpdate = new ProductEntity();
            mapProductDtoToEntity(productDto, productEntityUpdate, true);
            return productRepository.save(productEntityUpdate);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        productEntity.ifPresent(entity -> productRepository.delete(entity));
    }

    private ProductDto mapToDto(ProductEntity product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setStock_quantity(product.getStock_quantity());
        return dto;
    }

    private void mapProductDtoToEntity(ProductDto dto, ProductEntity entity, Boolean isUpdate) {
        if (isUpdate) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setStock_quantity(dto.getStock_quantity());

        BrandEntity brand = brandService.getBrandEntityById(dto.getBrand_id());
        entity.setBrand(brand);

        CategoryEntity category = categoryService.getCategoryEntityById(dto.getCategory_id());
        entity.setCategory(category);
    }
}
