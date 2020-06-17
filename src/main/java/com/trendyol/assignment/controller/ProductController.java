package com.trendyol.assignment.controller;

import com.trendyol.assignment.exceptions.BadRequestException;
import com.trendyol.assignment.exceptions.NotFoundException;
import com.trendyol.assignment.model.Category;
import com.trendyol.assignment.model.CreateProductRequest;
import com.trendyol.assignment.model.Product;
import com.trendyol.assignment.service.CategoryService;
import com.trendyol.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/products", consumes = "application/json")
    public Product createProduct(@RequestBody CreateProductRequest request) {

        if (request.getCategoryId() == null) {
            throw new BadRequestException("Category Id is missing from the request.");
        }

        return productService.createProduct(request);
    }

    @GetMapping(value = "/products/{categoryId}")
    public Set<Product> getProductsByCategory(@PathVariable("categoryId") long id) {

        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " does not exist!"));

        return category.getProducts();
    }

    @PutMapping(value = "/products")
    public Product updateProduct(@RequestBody Product product) {

        return productService.updateProduct(product);
    }
}
