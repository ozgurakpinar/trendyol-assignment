package com.trendyol.assignment.service;

import com.trendyol.assignment.exceptions.NotFoundException;
import com.trendyol.assignment.model.CreateProductRequest;
import com.trendyol.assignment.model.Product;
import com.trendyol.assignment.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.trendyol.assignment.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductService underTest;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateProduct() {
        // Given
        CreateProductRequest request = mock(CreateProductRequest.class);
        Product mockProduct = mock(Product.class);

        when(request.getProduct()).thenAnswer(invocation -> mockProduct);
        when(request.getCategoryId()).thenAnswer(invocation -> ANY_CATEGORY_ID);
        when(categoryService.getCategoryById(ANY_CATEGORY_ID)).thenReturn(Optional.of(ANY_CATEGORY));

        // When
        Product product = underTest.createProduct(request);

        // Verify
        verify(categoryService, times(1)).getCategoryById(ANY_CATEGORY_ID);
        verify(productRepository, times(1)).save(mockProduct);
        verify(mockProduct, times(1)).setCategory(ANY_CATEGORY);
    }

    @Test
    public void testCreateProductThrowsWhenCategoryNotFound() {
        // Given
        CreateProductRequest request = mock(CreateProductRequest.class);
        when(categoryService.getCategoryById(anyLong())).thenReturn(Optional.empty());

        // When and Verify
        Assertions.assertThrows(NotFoundException.class, () -> {
            underTest.createProduct(request);
        });
    }

    @Test
    public void testGetProductById() {
        // Given
        long productId = 1;

        Product mockProduct = mock(Product.class);
        when(mockProduct.getId()).thenAnswer(invocation -> productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // When
        Optional<Product> product = underTest.getProductById(productId);

        // Verify
        verify(productRepository, times(1)).findById(productId);
        assertEquals(productId, product.get().getId());
    }

    @Test
    public void testUpdateProduct() {

        // Given
        Product mockProduct = mock(Product.class);
        Product mockModifiedProduct = mock(Product.class);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(mockProduct));
        when(productRepository.save(mockProduct)).thenReturn(mockProduct);

        when(mockModifiedProduct.getId()).thenReturn(ANY_PRODUCT_ID);
        when(mockModifiedProduct.getDescription()).thenReturn(ANY_PRODUCT_DESCRIPTION);
        when(mockModifiedProduct.getName()).thenReturn(ANY_PRODUCT_NAME);
        when(mockModifiedProduct.getPrice()).thenReturn(ANY_PRODUCT_PRICE);
        when(mockModifiedProduct.getCategory()).thenReturn(ANY_CATEGORY);

        // When
        Product updatedProduct = underTest.updateProduct(mockModifiedProduct);

        // Verify
        verify(productRepository, times(1)).findById(ANY_PRODUCT_ID);

        verify(mockProduct, times(1)).setName(ANY_PRODUCT_NAME);
        verify(mockProduct, times(1)).setDescription(ANY_PRODUCT_DESCRIPTION);
        verify(mockProduct, times(1)).setPrice(ANY_PRODUCT_PRICE);
        verify(mockProduct, times(1)).setCategory(ANY_CATEGORY);

        verify(productRepository, times(1)).save(mockProduct);

        assertEquals(mockProduct, updatedProduct);
    }

    @Test
    public void testUpdateProductThrowsWhenProductNotFound() {
        // Given
        Product mockProduct = mock(Product.class);
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When and Verify
        Assertions.assertThrows(NotFoundException.class, () -> {
            underTest.updateProduct(mockProduct);
        });
    }
}