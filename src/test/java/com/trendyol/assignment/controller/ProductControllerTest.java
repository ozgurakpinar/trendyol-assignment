package com.trendyol.assignment.controller;

import com.trendyol.assignment.exceptions.BadRequestException;
import com.trendyol.assignment.exceptions.NotFoundException;
import com.trendyol.assignment.model.Category;
import com.trendyol.assignment.model.CreateProductRequest;
import com.trendyol.assignment.model.Product;
import com.trendyol.assignment.service.CategoryService;
import com.trendyol.assignment.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.trendyol.assignment.utils.TestUtils.ANY_CATEGORY_ID;
import static com.trendyol.assignment.utils.TestUtils.ANY_PRODUCT_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductController underTest;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateProduct() {
        // Given
        CreateProductRequest request = mock(CreateProductRequest.class);
        Product mockProduct = mock(Product.class);

        when(request.getCategoryId()).thenReturn(ANY_CATEGORY_ID);
        when(productService.createProduct(request)).thenReturn(mockProduct);

        // When
        Product product = underTest.createProduct(request);

        // Verify
        verify(productService, times(1)).createProduct(request);
        assertEquals(mockProduct, product);
    }

    @Test
    public void testCreateProductThrowsWhenCategoryIdIsNull() {
        // Given
        CreateProductRequest request = mock(CreateProductRequest.class);
        when(request.getCategoryId()).thenReturn(null);

        // When and Verify
        Assertions.assertThrows(BadRequestException.class, () -> {
            underTest.createProduct(request);
        });
    }

    @Test
    public void testGetProductsByCategory() {

        // Given
        Category mockCategory = mock(Category.class);
        List<Product> mockProducts = mock(List.class);

        when(productService.getProductsByCategory(ANY_CATEGORY_ID)).thenReturn(mockProducts);

        // When
        List<Product> products = underTest.getProductsByCategory(ANY_CATEGORY_ID);

        // Verify
        verify(productService, times(1)).getProductsByCategory(ANY_CATEGORY_ID);
        assertEquals(mockProducts, products);
    }

    @Test
    public void testGetProductById() {

        // Given
        Category mockCategory = mock(Category.class);
        Product mockProduct = mock(Product.class);

        when(productService.getProductById(ANY_PRODUCT_ID)).thenReturn(Optional.of(mockProduct));

        // When
        Product product = underTest.getProductById(ANY_PRODUCT_ID);

        // Verify
        verify(productService, times(1)).getProductById(ANY_PRODUCT_ID);
        assertEquals(mockProduct, product);
    }

    @Test
    public void testGetProductByIdThrowsWhenProductNotFound() {

        // Given
        when(productService.getProductById(anyLong())).thenReturn(Optional.empty());

        // When and Verify
        Assertions.assertThrows(NotFoundException.class, () -> {
            underTest.getProductById(ANY_PRODUCT_ID);
        });
    }

    @Test
    public void testUpdateProduct() {

        // Given
        Product mockProduct = mock(Product.class);
        when(productService.updateProduct(mockProduct)).thenReturn(mockProduct);

        // When
        Product updatedProduct = underTest.updateProduct(mockProduct);

        // Verify
        verify(productService, times(1)).updateProduct(mockProduct);
        assertEquals(mockProduct, updatedProduct);
    }
}