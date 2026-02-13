package com.shrijal.e_commerce.service;

import com.shrijal.e_commerce.dto.request.CategoryRequestDTO;
import com.shrijal.e_commerce.model.Category;
import com.shrijal.e_commerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Category operations
 * Handles business logic for category management
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Create a new category
     * @param dto - category data
     * @return Created Category entity
     */
    public Category create(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return categoryRepository.save(category);
    }

    /**
     * Get all categories
     * @return List of all Category entities
     */
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    /**
     * Get category by ID
     * @param id - category ID
     * @return Category entity
     * @throws RuntimeException if category not found
     */
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    /**
     * Delete category by ID
     * @param id - category ID
     */
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
