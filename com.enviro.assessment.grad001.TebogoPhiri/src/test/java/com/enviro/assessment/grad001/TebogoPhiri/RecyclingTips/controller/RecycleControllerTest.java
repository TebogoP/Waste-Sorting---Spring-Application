package com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.controller;

import com.enviro.assessment.grad001.TebogoPhiri.Generic.Service.JdbcClientRepos;
import com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.model.RecyclingTip;
import com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.service.RecycleRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecycleController.class)
class RecycleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecycleRepository recyclingRepository;

    @MockBean
    private JdbcClientRepos jdbcRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private RecyclingTip sampleTip;

    @BeforeEach
    void setup() {
        sampleTip = new RecyclingTip(1, "Rinse metal cans before recycling.", "Metal");
    }

    @Test
    void testFindAll() throws Exception {
        Mockito.when(recyclingRepository.findAll()).thenReturn(Arrays.asList(sampleTip));

        //Assert
        mockMvc.perform(get("/api/waste/recycle"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(sampleTip.getId()))
                .andExpect(jsonPath("$[0].tip").value(sampleTip.getTip()))
                .andExpect(jsonPath("$[0].category").value(sampleTip.getCategory()));
    }

    @Test
    void testFindById_Success() throws Exception {
        Mockito.when(recyclingRepository.findById(1)).thenReturn(Optional.of(sampleTip));

        //Assert
        mockMvc.perform(get("/api/waste/recycle/1"))
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(sampleTip.getId()))
                .andExpect(jsonPath("$.tip").value(sampleTip.getTip()))
                .andExpect(jsonPath("$.category").value(sampleTip.getCategory()));
    }

    @Test
    void testCreateTip_Success() throws Exception {
        Mockito.when(recyclingRepository.existsById(1)).thenReturn(false);

        RecyclingTip newTip = new RecyclingTip(1, "Use separate bins for glass, plastic, and paper.", "General");

        //Assert
        mockMvc.perform(post("/api/waste/recycle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTip)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Recycle tip created successfully"));
    }
}
