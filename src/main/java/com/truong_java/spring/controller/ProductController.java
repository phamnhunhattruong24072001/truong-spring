package com.truong_java.spring.controller;

import com.truong_java.spring.dto.ProductDto;
import com.truong_java.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController extends BaseController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<ProductDto> products = productService.getAllProducts();
        return ok(products);
    }
}
