package com.trendyol.assignment.service;

import com.trendyol.assignment.exceptions.NotFoundException;
import com.trendyol.assignment.model.Category;
import com.trendyol.assignment.model.CreateProductRequest;
import com.trendyol.assignment.model.Product;
import com.trendyol.assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public Product createProduct(CreateProductRequest request){

        Category category = categoryService.getCategoryById(request.getCategoryId())
                .orElseThrow(() -> {
                    return new NotFoundException("Category with id " + request.getCategoryId() + " does not exist!");
                });

        request.getProduct().setCategory(category);
        return productRepository.save(request.getProduct());
    }

    public Optional<Product> getProductById(long id){
        return productRepository.findById(id);
    }

    public Product updateProduct(Product product) {

        Product originalProduct = getProductById(product.getId())
                .orElseThrow(() -> new NotFoundException("Product with id " + product.getId() + " does not exist!"));

        originalProduct.setName(product.getName());
        originalProduct.setPrice(product.getPrice());
        originalProduct.setDescription(product.getDescription());
        originalProduct.setCategory(product.getCategory());

        return productRepository.save(originalProduct);
    }
}
