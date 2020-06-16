package com.trendyol.assignment.service;

import com.trendyol.assignment.model.Product;
import com.trendyol.assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product){
        return repository.save(product);
    }

    public Optional<Product> getProductById(long id){
        return repository.findById(id);
    }
}
