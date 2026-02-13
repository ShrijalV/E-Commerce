package com.shrijal.e_commerce.service;

import com.shrijal.e_commerce.dto.request.ProductRequestDTO;
import com.shrijal.e_commerce.dto.response.ProductResponseDTO;
import com.shrijal.e_commerce.model.Category;
import com.shrijal.e_commerce.model.Product;
import com.shrijal.e_commerce.repository.CategoryRepository;
import com.shrijal.e_commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    //create new product
    //Throw exception if category not found
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        // Verify if category exists
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));

        // Create product entity
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(category);

        // Save product
        Product saved = productRepository.save(product);

        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setDescription(saved.getDescription());
        response.setPrice(saved.getPrice());
        response.setStockQuantity(saved.getStockQuantity());
        response.setCategoryName(saved.getCategory().getName());

        return response;
    }


     //get the product
    //throws Exception if product not found

    public ProductResponseDTO getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        // Convert to response DTO
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setCategoryName(product.getCategory().getName());

        return dto;
    }


     //Update product stock quantity

    @Transactional
    public void updateStock(Long productId, Integer newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        product.setStockQuantity(newStock);
        productRepository.save(product);
    }
}