package com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.controller;

import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.model.WasteCategory;
import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.service.JdbcClientRepos;
import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.service.WasteCatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WasteCateControlTest {

    private MockMvc mockMvc;

    @Mock
    private WasteCatRepository wasteCategoryRepository;

    @Mock
    private JdbcClientRepos jdbcClientRepository;

    @InjectMocks
    private WasteCatController wasteCategoryController;

    private WasteCategory sampleCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(wasteCategoryController).build();

        sampleCategory = new WasteCategory(1, "Glass Waste", "Recyclable glass materials.");
    }

    @Test
    void testFindAll() throws Exception {
        when(wasteCategoryRepository.findAll()).thenReturn(Arrays.asList(sampleCategory));

        mockMvc.perform(get("/api/waste/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleCategory.getId()))
                .andExpect(jsonPath("$[0].name").value(sampleCategory.getName()))
                .andExpect(jsonPath("$[0].description").value(sampleCategory.getDescription()));
    }

    @Test
    void testFindById_Success() throws Exception {
        when(wasteCategoryRepository.findById(1)).thenReturn(Optional.of(sampleCategory));

        mockMvc.perform(get("/api/waste/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleCategory.getId()))
                .andExpect(jsonPath("$.name").value(sampleCategory.getName()))
                .andExpect(jsonPath("$.description").value(sampleCategory.getDescription()));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(wasteCategoryRepository.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/waste/category/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("WasteCategory not found with ID 999"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("WasteCategory not found with ID 999"));
    }

    @Test
    void testCreateCategory() throws Exception {
        String newCategoryJson = """
                {   
                    "id": 2,
                    "name": "Paper Waste",
                    "description": "Recyclable paper materials."
                }
                """;

        mockMvc.perform(post("/api/waste/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCategoryJson))
                .andExpect(status().isCreated());

        verify(wasteCategoryRepository, times(1)).save(any(WasteCategory.class));
    }

    @Test
    void testUpdateCategory_Success() throws Exception {
        String updatedCategoryJson = """
                {
                    "id": 1,
                    "name": "Updated Glass Waste",
                    "description": "Updated recyclable glass materials."
                }
                """;

        when(wasteCategoryRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(put("/api/waste/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCategoryJson))
                .andExpect(status().isNoContent());

        verify(jdbcClientRepository, times(1)).update(any(WasteCategory.class), eq(1));
    }

    @Test
    void testUpdateCategory_NotFound() throws Exception {
        String updatedCategoryJson = """
                {
                    "id": 1,
                    "name": "Updated Glass Waste",
                    "description": "Updated recyclable glass materials."
                }
                """;

        when(wasteCategoryRepository.existsById(1)).thenReturn(false);

        mockMvc.perform(put("/api/waste/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCategoryJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCategory_Success() throws Exception {
        when(wasteCategoryRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/api/waste/category/1"))
                .andExpect(status().isNoContent());

        verify(wasteCategoryRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteCategory_NotFound() throws Exception {
        when(wasteCategoryRepository.existsById(999)).thenReturn(false);

        mockMvc.perform(delete("/api/waste/category/999"))
                .andExpect(status().isNotFound());
    }
}
