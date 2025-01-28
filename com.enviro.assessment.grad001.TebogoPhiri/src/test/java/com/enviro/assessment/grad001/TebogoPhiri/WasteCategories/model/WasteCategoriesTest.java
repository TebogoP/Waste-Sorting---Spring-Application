package com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WasteCategoriesTest {

    @Test
    void testWasteCategoriesCreationWithValidData() {
        WasteCategory category1 = new WasteCategory(1, "Organic Waste", "Biodegradable waste from plants.");
        WasteCategory category2 = new WasteCategory(2, "Plastic Waste", "Non-biodegradable synthetic materials.");
        List<WasteCategory> wasteList = List.of(category1, category2);

        WasteCategories wasteCategories = new WasteCategories(wasteList);

        // Assert
        assertNotNull(wasteCategories, "WasteCategories object should not be null.");
        assertEquals(2, wasteCategories.waste().size(), "Waste list should contain 2 categories.");
        assertEquals("Organic Waste", wasteCategories.waste().get(0).getName(), "First category name should match.");
    }

    @Test
    void testWasteCategoriesCreationWithEmptyList() {
        List<WasteCategory> emptyWasteList = List.of();

        WasteCategories wasteCategories = new WasteCategories(emptyWasteList);

        // Assert
        assertNotNull(wasteCategories, "WasteCategories object should not be null.");
        assertTrue(wasteCategories.waste().isEmpty(), "Waste list should be empty.");
    }

    @Test
    void testWasteCategoriesCreationWithNullList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new WasteCategories(null);
        });
        assertEquals("Waste categories list cannot be null", exception.getMessage(), "Exception message should match.");
    }

    @Test
    void testToStringMethod() {
        WasteCategory category = new WasteCategory(1, "Metal Waste", "Discarded metal materials.");
        WasteCategories wasteCategories = new WasteCategories(List.of(category));

        String result = wasteCategories.toString();

        // Assert
        assertTrue(result.contains("Metal Waste"), "toString should include the waste category name.");
        assertTrue(result.contains("Discarded metal materials."), "toString should include the waste category description.");
    }
}
