package com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.controller;

import com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.model.DisposalGuideline;
import com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.service.DisGuideRepository;
import com.enviro.assessment.grad001.TebogoPhiri.Generic.Service.JdbcClientRepos;
import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.service.JdbcClientReposWC;
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

@WebMvcTest(DisGuideController.class)
class DisGuideControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcClientRepos jdbcRepository;


    @MockBean
    private DisGuideRepository disguideRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private DisposalGuideline sampleGuide;

    @BeforeEach
    void setup() {
        sampleGuide = new DisposalGuideline(1, "Plastic Waste", "Dispose of plastics in recycling bins.");
    }

    @Test
    void testFindAll() throws Exception {
        // Arrange
        Mockito.when(disguideRepository.findAll()).thenReturn(Arrays.asList(sampleGuide));

        //Assert
        mockMvc.perform(get("/api/waste/disposal"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(sampleGuide.getId()))
                .andExpect(jsonPath("$[0].wasteType").value(sampleGuide.getWasteType()))
                .andExpect(jsonPath("$[0].guideline").value(sampleGuide.getGuideline()));
    }

    @Test
    void testFindById_Success() throws Exception {
        Mockito.when(disguideRepository.findById(1)).thenReturn(Optional.of(sampleGuide));

        //Assert
        mockMvc.perform(get("/api/waste/disposal/1"))
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(sampleGuide.getId()))
                .andExpect(jsonPath("$.wasteType").value(sampleGuide.getWasteType()))
                .andExpect(jsonPath("$.guideline").value(sampleGuide.getGuideline()));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        Mockito.when(disguideRepository.findById(1)).thenReturn(Optional.empty());

        //Assert
        mockMvc.perform(get("/api/waste/disposal/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("Disposal guideline not found with ID 1")));
    }

    @Test
    void testCreateGuide_Success() throws Exception {
        Mockito.when(disguideRepository.existsById(1)).thenReturn(false);

        DisposalGuideline newGuide = new DisposalGuideline(1, "Metal Waste", "Separate metals for recycling.");

        //Assert
        mockMvc.perform(post("/api/waste/disposal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newGuide)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Disposal guideline created successfully"));
    }

    @Test
    void testCreateGuide_Conflict() throws Exception {
        Mockito.when(disguideRepository.existsById(1)).thenReturn(true);

        //Assert
        mockMvc.perform(post("/api/waste/disposal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleGuide)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(containsString("Disposal guideline with ID 1 already exists")));
    }

    @Test
    void testDeleteGuide_Success() throws Exception {
        Mockito.when(disguideRepository.existsById(1)).thenReturn(true);

        //Assert
        mockMvc.perform(delete("/api/waste/disposal/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Disposal guideline deleted successfully")));
    }

    @Test
    void testDeleteGuide_NotFound() throws Exception {
        Mockito.when(disguideRepository.existsById(1)).thenReturn(false);

        //Assert
        mockMvc.perform(delete("/api/waste/disposal/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("Disposal guideline not found with ID 1")));
    }
}
