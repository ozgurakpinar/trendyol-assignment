package com.trendyol.assignment.controller;

import com.trendyol.assignment.exceptions.NotFoundException;
import com.trendyol.assignment.model.Category;
import com.trendyol.assignment.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/categories", consumes = "application/json")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @GetMapping(value = "/categories")
    public List<Category> getAllCategories() {
        return categoryService.getCategories();
    }

    @GetMapping(value = "/categories/{id}")
    public Category getCategory(@PathVariable("id") long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        category.orElseThrow(() -> new NotFoundException("Category with id " + id + " does not exist!"));

        return category.get();
    }
}
