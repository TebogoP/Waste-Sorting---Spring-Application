package com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.JSON;

import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.model.WasteCategories;
import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.service.WasteCategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class JsonDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(JsonDataLoader.class);
    private final ObjectMapper objectMapper;
    private final WasteCategoryRepository wasteCategoryRepository;

    public JsonDataLoader(ObjectMapper objectMapper, WasteCategoryRepository wasteCategoryRepository) {
        this.objectMapper = objectMapper;
        this.wasteCategoryRepository = wasteCategoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        if (wasteCategoryRepository.count() == 0) {
            try (InputStream inputStream = getClass().getResourceAsStream("/data/waste-categories.json")) {
                if (inputStream == null) {
                    log.error("Resource file {} not found!", "/data/waste-categories.json");
                    return;
                }
                WasteCategories allWasteCategories = objectMapper.readValue(inputStream, WasteCategories.class);
                wasteCategoryRepository.saveAll(allWasteCategories.waste());
                log.info("Loaded {} WasteCategories from JSON data into the database.", allWasteCategories.waste().size());
            } catch (IOException e) {
                log.error("Failed to read JSON data from resource path: {}", "/data/waste-categories.json", e);
                throw new RuntimeException("Failed to load WasteCategories data from JSON file", e);
            }
        } else {
            log.info("Skipped loading WasteCategories. Database already contains data.");
        }
    }
}
