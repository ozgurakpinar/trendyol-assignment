package com.trendyol.assignment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.trendyol.assignment.model.Category;
import com.trendyol.assignment.model.CreateProductRequest;
import com.trendyol.assignment.model.Product;
import com.trendyol.assignment.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.trendyol.assignment.utils.IntTestUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application.properties")
public class ProductControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    private Category category;

    @BeforeEach
    public void setUp() throws Exception {

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // Create Category
        String categoryJson = mockMvc.perform(createCategoryRequest(ANY_CATEGORY))
                .andReturn()
                .getResponse()
                .getContentAsString();

        category = objectMapper.readValue(categoryJson, Category.class);
    }

    @AfterEach
    public void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    public void testCreateProduct() throws Exception {

        // When
        MockHttpServletRequestBuilder request = createProductRequest(ANY_PRODUCT, category);

        // Verify
        mockMvc.perform(request)
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(ANY_PRODUCT.getName()))
                .andExpect(jsonPath("$.description").value(ANY_PRODUCT.getDescription()));
    }

    @Test
    public void testGetProductsByCategory() throws Exception {

        // Given
        int numberOfProducts = 3;

        for (int i = 0; i < numberOfProducts; i++) {
            mockMvc.perform(createProductRequest(ANY_PRODUCT, category));
        }

        // When
        MockHttpServletRequestBuilder builder = get("/products/categories/" + category.getId())
                .accept(APPLICATION_JSON)
                .header("Authorization", "Basic YWRtaW46MTIzNDU2");

        // Verify
        mockMvc.perform(builder)
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$", hasSize(numberOfProducts)));
    }

    @Test
    public void testGetProductById() throws Exception {

        // Given
        String productJsonString = mockMvc.perform(createProductRequest(ANY_PRODUCT, category))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Product product = objectMapper.readValue(productJsonString, Product.class);

        // When
        MockHttpServletRequestBuilder builder = get("/products/" + product.getId())
                .contentType(APPLICATION_JSON)
                .header("Authorization", "Basic YWRtaW46MTIzNDU2");

        // Verify
        mockMvc.perform(builder)
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.category.id").value(category.getId()));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Given
        String productJsonString = mockMvc.perform(createProductRequest(ANY_PRODUCT, category))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Product product = objectMapper.readValue(productJsonString, Product.class);

        // When

        product.setName(ANY_OTHER_PRODUCT.getName());
        product.setDescription(ANY_OTHER_PRODUCT.getDescription());
        product.setPrice(ANY_OTHER_PRODUCT.getPrice());

        MockHttpServletRequestBuilder requestBuilder = put("/products")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(product))
                    .header("Authorization", "Basic YWRtaW46MTIzNDU2");

        // Verify
        mockMvc.perform(requestBuilder)
                .andExpect(jsonPath("$.name").value(ANY_OTHER_PRODUCT.getName()))
                .andExpect(jsonPath("$.description").value(ANY_OTHER_PRODUCT.getDescription()))
                .andExpect(jsonPath("$.price").value(ANY_OTHER_PRODUCT.getPrice()));
    }

    private MockHttpServletRequestBuilder createProductRequest(Product product, Category category) throws JsonProcessingException {

        CreateProductRequest createProductRequestObject = new CreateProductRequest();
        createProductRequestObject.setCategoryId(category.getId());
        createProductRequestObject.setProduct(product);

        return post("/products")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProductRequestObject))
                .header("Authorization", "Basic YWRtaW46MTIzNDU2");
    }

    private MockHttpServletRequestBuilder createCategoryRequest(Category category) throws JsonProcessingException {

        return post("/categories")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category))
                .header("Authorization", "Basic YWRtaW46MTIzNDU2");
    }
}
