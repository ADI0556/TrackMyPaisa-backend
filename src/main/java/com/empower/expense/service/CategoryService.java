package com.empower.expense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empower.expense.model.Category;
import com.empower.expense.repository.CategoryRepository;


@Service
public class CategoryService {

	@Autowired
    private CategoryRepository categoryRepository;

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    public List<Category> findAllOrderedByName() {
        return categoryRepository.findAllByOrderByCategoryNameAsc();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
