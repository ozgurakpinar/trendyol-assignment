package com.trendyol.assignment.service;

import com.trendyol.assignment.model.Category;
import com.trendyol.assignment.model.Product;
import com.trendyol.assignment.repository.ProductRepository;
import com.trendyol.assignment.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService underTest;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveProduct() {
        // Given
        Product product = mock(Product.class);

        // When
        underTest.saveProduct(product);

        // Verify
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testGetProductById() {
        // Given
        long productId = 1;

        Product mockProduct = mock(Product.class);
        when(mockProduct.getId()).thenAnswer(invocation -> productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // When
        Product product = underTest.getProductById(productId);

        // Verify
        verify(productRepository, times(1)).findById(productId);
        assertEquals(productId, product.getId());
    }
}