package com.empower.expense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empower.expense.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Custom query method to find a category by its name
    Category findByCategoryName(String categoryName);

    // Additional query method to find all categories sorted by category name
    List<Category> findAllByOrderByCategoryNameAsc();
}