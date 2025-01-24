package com.enviro.assessment.grad001.TebogoPhiri.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WasteCategoryTest {

    @Test
    void testValidWasteCategoryCreation() {
        // Positive Test Case
        WasteCategory category = new WasteCategory(1, "Organic Waste",
                "Biodegradable waste originating from plants, animals, or other organic materials.");

        assertEquals(1, category.getId());
        assertEquals("Organic Waste", category.getName());
        assertEquals("Biodegradable waste originating from plants, animals, or other organic materials.", category.getDescription());
    }

    @Test
    void testValidWasteCategoryElectronicWaste() {
        // Positive Test Case
        WasteCategory category = new WasteCategory(2, "Electronic Waste (E-Waste)",
                "Old or discarded electronic devices such as computers and mobile phones.");

        assertEquals(2, category.getId());
        assertEquals("Electronic Waste (E-Waste)", category.getName());
        assertEquals("Old or discarded electronic devices such as computers and mobile phones.", category.getDescription());
    }

    @Test
    void testWasteCategoryWithMissingName() {
        // Negative Test Case: Name is null
        WasteCategory category = new WasteCategory(3, null,
                "Waste generated during the extraction of minerals and metals.");

        assertNull(category.getName());
        assertEquals(3, category.getId());
        assertEquals("Waste generated during the extraction of minerals and metals.", category.getDescription());
    }

    @Test
    void testWasteCategoryWithEmptyDescription() {
        // Negative Test Case: Description is empty
        WasteCategory category = new WasteCategory(4, "Metal Waste", "");

        assertEquals(4, category.getId());
        assertEquals("Metal Waste", category.getName());
        assertTrue(category.getDescription().isEmpty());
    }

    @Test
    void testWasteCategoryWithNegativeId() {
        // Negative Test Case: ID is negative
        WasteCategory category = new WasteCategory(-5, "Plastic Waste",
                "Non-biodegradable waste made from synthetic polymers like plastic bottles and bags.");

        assertTrue(category.getId() < 0, "ID should not be negative");
        assertEquals("Plastic Waste", category.getName());
        assertEquals("Non-biodegradable waste made from synthetic polymers like plastic bottles and bags.", category.getDescription());
    }

    @Test
    void testToStringMethod() {
        // Positive Test Case
        WasteCategory category = new WasteCategory(6, "Paper Waste",
                "Discarded paper materials such as newspapers, cardboard, and office paper.");
        String expectedString = "{\"id\": 6, \"name\": \"Paper Waste\", \"description\": \"Discarded paper materials such as newspapers, cardboard, and office paper.\"}";
        assertEquals(expectedString, category.toString());
    }
}
