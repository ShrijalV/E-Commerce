package com.shrijal.e_commerce.repository;

import com.shrijal.e_commerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find category by name (useful for validation)
    Optional<Category> findByName(String name);
}
