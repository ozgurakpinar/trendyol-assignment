package com.trendyol.assignment.repository;

import com.trendyol.assignment.model.Category;
import com.trendyol.assignment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
