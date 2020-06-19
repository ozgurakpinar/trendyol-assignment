package com.trendyol.assignment.repository;

import com.trendyol.assignment.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static com.trendyol.assignment.utils.IntTestUtils.ANY_CATEGORY;
import static com.trendyol.assignment.utils.IntTestUtils.ANY_PRODUCT;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class ProductRepositoryIntTest {

    @Autowired
    private ProductRepository underTest;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    public void setUp() {
        ANY_CATEGORY.setId(null);
        categoryRepository.save(ANY_CATEGORY);

        underTest.deleteAll();
    }

    @BeforeEach
    public void setUpBeforeMethod() {
        ANY_PRODUCT.setId(null);
    }

    @AfterEach
    public void cleanUp() {
        underTest.deleteAll();
    }

    @Test
    public void testSaveProduct() {

        // When
        Product product = underTest.save(ANY_PRODUCT);

        // Verify
        assertNotNull(product.getId());
    }

    @Test
    public void testFindById() {

        // Given
        Product product = underTest.save(ANY_PRODUCT);

        // When
        Optional<Product> optionalProduct = underTest.findById(product.getId());

        // Verify
        assertTrue(optionalProduct.isPresent());
        assertEquals(product.getId(), optionalProduct.get().getId());
    }

    @Test
    public void testFindByCategory() {
        // Given
        Product product = underTest.save(ANY_PRODUCT);

        // When
        List<Product> products = underTest.findByCategory(ANY_CATEGORY);

        // Verify
        assertEquals(1, products.size());
        assertEquals(product.getId(), products.get(0).getId());
    }
}
