package com.trendyol.assignment.utils;

import com.trendyol.assignment.model.Category;
import com.trendyol.assignment.model.Product;

import java.math.BigDecimal;

public class IntTestUtils {

    public static final Category ANY_CATEGORY = createCategory("Category1", "Category Description 1");
    public static final Category ANY_OTHER_CATEGORY = createCategory("Category2", "Category Description 2");

    public static final Product ANY_PRODUCT =
            createProduct("Product 1", "Product Description", BigDecimal.valueOf(0.5), ANY_CATEGORY);
    public static final Product ANY_OTHER_PRODUCT =
            createProduct("Product 2", "Product Description 2", BigDecimal.valueOf(0.6), ANY_OTHER_CATEGORY);

    private static Product createProduct(String name, String description, BigDecimal price, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        return product;
    }

    private static Category createCategory(String name, String description) {
        Category category = new Category();
        category.setDescription(description);
        category.setName(name);

        return category;
    }
}
