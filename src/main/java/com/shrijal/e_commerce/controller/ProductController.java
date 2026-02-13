package com.shrijal.e_commerce.controller;

import com.shrijal.e_commerce.dto.request.ProductRequestDTO;
import com.shrijal.e_commerce.dto.response.ProductResponseDTO;
import com.shrijal.e_commerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //create product
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO response = productService.createProduct(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //get product by id
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> get(@PathVariable Long id) {
        ProductResponseDTO response = productService.getProduct(id);
        return ResponseEntity.ok(response);
    }
}
