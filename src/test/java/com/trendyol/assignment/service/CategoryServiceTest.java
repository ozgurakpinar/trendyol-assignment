package com.trendyol.assignment.service;

import com.trendyol.assignment.model.Category;
import com.trendyol.assignment.model.Product;
import com.trendyol.assignment.repository.CategoryRepository;
import com.trendyol.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService underTest;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveCategory() {
        // Given
        Category category = mock(Category.class);

        // When
        underTest.saveCategory(category);

        // Verify
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testGetCategoryById() {
        // Given
        long categoryId = 1;

        Category mockCategory = mock(Category.class);
        when(mockCategory.getId()).thenAnswer(invocation -> categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(mockCategory));

        // When
        Category category = underTest.getCategoryById(categoryId);

        // Verify
        verify(categoryRepository, times(1)).findById(categoryId);
        assertEquals(categoryId, category.getId());
    }

    @Test
    public void testGetAllCategories() {
        // Given
        List<Category> mockList = mock(List.class);
        when(categoryRepository.findAll()).thenReturn(mockList);

        // When
        List<Category> categories = underTest.getCategories();

        // Verify
        verify(categoryRepository, times(1)).findAll();
        assertEquals(mockList, categories);
    }
}