package com.trendyol.assignment.repository;

import com.trendyol.assignment.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static com.trendyol.assignment.utils.IntTestUtils.ANY_CATEGORY;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class CategoryRepositoryIntTest {

    @Autowired
    private CategoryRepository underTest;

    @AfterEach
    public void cleanUp() {
        underTest.deleteAll();
    }

    @BeforeAll
    public void setUp() {
        underTest.deleteAll();
        ANY_CATEGORY.setId(null);
    }

    @Test
    public void testSave() {
        // When
        Category category = underTest.save(ANY_CATEGORY);

        // Verify
        assertNotNull(category.getId());
    }

    @Test
    public void testFindById() {
        // Given
        Category category = underTest.save(ANY_CATEGORY);

        // When
        Optional<Category> optionalCategory = underTest.findById(category.getId());

        assertTrue(optionalCategory.isPresent());
        assertEquals(category.getId(), optionalCategory.get().getId());
    }
}
