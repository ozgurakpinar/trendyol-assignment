package com.trendyol.assignment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.assignment.model.Category;
import com.trendyol.assignment.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.trendyol.assignment.utils.IntTestUtils.ANY_CATEGORY;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application.properties")
public class CategoryControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    public void cleanUp() {
        categoryRepository.deleteAll();
    }

    @Test
    public void testCreateCategory() throws Exception {

        // When
        MockHttpServletRequestBuilder createCategory = createCategoryRequest(ANY_CATEGORY);

        // Verify
        mockMvc.perform(createCategory)
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(ANY_CATEGORY.getName()))
                .andExpect(jsonPath("$.description").value(ANY_CATEGORY.getDescription()));
    }

    @Test
    public void testGetAllCategories() throws Exception {

        // Given
        int numberOfCategories = 3;

        for (int i = 0; i < numberOfCategories; i++) {
            mockMvc.perform(createCategoryRequest(ANY_CATEGORY));
        }

        // When
        MockHttpServletRequestBuilder builder = get("/categories")
                .accept(APPLICATION_JSON)
                .header("Authorization", "Basic YWRtaW46MTIzNDU2");

        // Verify
        mockMvc.perform(builder)
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(numberOfCategories)));
    }

    @Test
    public void testGetCategoryById() throws Exception {
        // Given
        String categoryJson = mockMvc.perform(createCategoryRequest(ANY_CATEGORY))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Category createdCategory = objectMapper.readValue(categoryJson, Category.class);

        // When
        MockHttpServletRequestBuilder builder = get("/categories/" + createdCategory.getId())
                .accept(APPLICATION_JSON)
                .header("Authorization", "Basic YWRtaW46MTIzNDU2");

        // Verify
        mockMvc.perform(builder)
                .andExpect(jsonPath("$.id").value(createdCategory.getId()))
                .andExpect(jsonPath("$.name").value(createdCategory.getName()));
    }

    private MockHttpServletRequestBuilder createCategoryRequest(Category category) throws JsonProcessingException {

        return post("/categories")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category))
                .header("Authorization", "Basic YWRtaW46MTIzNDU2");
    }


}
