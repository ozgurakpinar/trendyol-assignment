package com.trendyol.assignment.controller;

import com.trendyol.assignment.exceptions.NotFoundException;
import com.trendyol.assignment.model.Category;
import com.trendyol.assignment.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.trendyol.assignment.utils.TestUtils.ANY_CATEGORY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController underTest;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCategory() {
        // Given
        Category mockCategory = mock(Category.class);

        // When
        underTest.createCategory(mockCategory);

        // Verify
        verify(categoryService, times(1)).saveCategory(mockCategory);
    }

    @Test
    public void testGetAllCategories() {
        // Given
        List<Category> mockCategories = mock(List.class);
        when(categoryService.getCategories()).thenReturn(mockCategories);

        // When
        List<Category> allCategories = underTest.getAllCategories();

        // Verify
        verify(categoryService, times(1)).getCategories();
        assertEquals(mockCategories, allCategories);
    }

    @Test
    public void testGetCategoryById() {
        // Given
        Category mockCategory = mock(Category.class);
        when(categoryService.getCategoryById(ANY_CATEGORY_ID)).thenReturn(Optional.of(mockCategory));

        // When
        Category category = underTest.getCategory(ANY_CATEGORY_ID);

        // Verify
        verify(categoryService, times(1)).getCategoryById(ANY_CATEGORY_ID);
        assertEquals(mockCategory, category);
    }

    @Test
    public void testGetCategoryByIdThrowsWhenCategoryNotFound() {

        // Given
        when(categoryService.getCategoryById(ANY_CATEGORY_ID)).thenReturn(Optional.empty());

        // When and Verify
        Assertions.assertThrows(NotFoundException.class, () -> {
            underTest.getCategory(ANY_CATEGORY_ID);
        });
    }
}