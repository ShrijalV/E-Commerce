package com.shrijal.e_commerce.controller;


import com.shrijal.e_commerce.dto.request.CategoryRequestDTO;
import com.shrijal.e_commerce.model.Category;
import com.shrijal.e_commerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //create category
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryRequestDTO dto) {
        Category category = categoryService.create(dto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    //get category
    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    //Get category
    @GetMapping("/{id}")
    public ResponseEntity<Category> get(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(category);
    }

    //delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}