package com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.controller;

import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.model.WasteCategory;
import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.service.JdbcClientReposWC;
import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.service.WasteCatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WasteCatController.class)
class WasteCatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WasteCatRepository wasteCatRepository;

    @MockBean
    private JdbcClientReposWC jdbcRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private WasteCategory sampleCategory;

    @BeforeEach
    void setup() {
        sampleCategory = new WasteCategory(1, "Plastic Waste", "Non-biodegradable synthetic materials.");
    }

    @Test
    void testFindAll() throws Exception {
        // Arrange
        Mockito.when(wasteCatRepository.findAll()).thenReturn(Arrays.asList(sampleCategory));

        // Act and Assert
        mockMvc.perform(get("/api/waste/category"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(sampleCategory.getId()))
                .andExpect(jsonPath("$[0].name").value(sampleCategory.getName()))
                .andExpect(jsonPath("$[0].description").value(sampleCategory.getDescription()));
    }

    @Test
    void testFindById_Success() throws Exception {
        // Arrange
        Mockito.when(wasteCatRepository.findById(1)).thenReturn(Optional.of(sampleCategory));

        // Act and Assert
        mockMvc.perform(get("/api/waste/category/1"))
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(sampleCategory.getId()))
                .andExpect(jsonPath("$.name").value(sampleCategory.getName()))
                .andExpect(jsonPath("$.description").value(sampleCategory.getDescription()));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        // Arrange
        Mockito.when(wasteCatRepository.findById(1)).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(get("/api/waste/category/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("WasteCategory not found with ID 1")));
    }

    @Test
    void testCreateCategory_Success() throws Exception {
        // Arrange: Mock repository to indicate the category ID does not exist

        WasteCategory newCategory = new WasteCategory(1, "Plastic Waste", "Non-biodegradable synthetic materials.");

        // Act and Assert: Simulate a POST request
        mockMvc.perform(post("/api/waste/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategory)))
                .andExpect(status().isCreated()) // Assert 201 Created
                .andExpect(content().string("WasteCategory created successfully")); // Assert success message

    }


    @Test
    void testCreateCategory_Conflict() throws Exception {
        // Arrange
        Mockito.when(wasteCatRepository.existsById(1)).thenReturn(true);

        // Act and Assert
        mockMvc.perform(post("/api/waste/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCategory)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(containsString("WasteCategory with ID 1 already exists")));
    }

    @Test
    void testUpdateCategory_Success() throws Exception {
        // Arrange
        Mockito.when(wasteCatRepository.existsById(1)).thenReturn(true);
        Mockito.doNothing().when(jdbcRepository).update(any(WasteCategory.class), eq(1));

        // Act and Assert
        mockMvc.perform(put("/api/waste/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCategory)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("WasteCategory updated successfully")));
    }

    @Test
    void testUpdateCategory_NotFound() throws Exception {
        // Arrange
        Mockito.when(wasteCatRepository.existsById(1)).thenReturn(false);

        // Act and Assert
        mockMvc.perform(put("/api/waste/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCategory)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("WasteCategory not found with ID 1")));
    }

    @Test
    void testDeleteCategory_Success() throws Exception {
        // Arrange
        Mockito.when(wasteCatRepository.existsById(1)).thenReturn(true);
        Mockito.doNothing().when(wasteCatRepository).deleteById(1);

        // Act and Assert
        mockMvc.perform(delete("/api/waste/category/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("WasteCategory deleted successfully")));
    }

    @Test
    void testDeleteCategory_NotFound() throws Exception {
        // Arrange
        Mockito.when(wasteCatRepository.existsById(1)).thenReturn(false);

        //Assert
        mockMvc.perform(delete("/api/waste/category/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("WasteCategory not found with ID 1")));
    }
}
