package com.trendyol.assignment.service;

import com.trendyol.assignment.model.Category;
import com.trendyol.assignment.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category saveCategory(Category category){
        return repository.save(category);
    }

    public Optional<Category> getCategoryById(long id) {
        return repository.findById(id);
    }

    public List<Category> getCategories(){
        return repository.findAll();
    }
}
