package com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecyclingTipsTest {
    @Test
    void testRecyclingTipsCreationWithValidData() {
        RecyclingTip tip1 = new RecyclingTip(1, "Flatten cardboard boxes", "Paper Waste");
        RecyclingTip tip2 = new RecyclingTip(2, "Rinse containers before recycling", "Plastic Waste");
        List<RecyclingTip> tipsList = List.of(tip1, tip2);

        RecyclingTips recyclingTips = new RecyclingTips(tipsList);

        // Assert
        assertNotNull(recyclingTips, "RecyclingTips object should not be null.");
        assertEquals(2, recyclingTips.tips().size(), "Tips list should contain 2 items.");
        assertEquals("Flatten cardboard boxes", recyclingTips.tips().get(0).getTip(), "First tip should match.");
    }

    @Test
    void testRecyclingTipsCreationWithEmptyList() {
        List<RecyclingTip> emptyTips = List.of();

        RecyclingTips recyclingTips = new RecyclingTips(emptyTips);

        // Assert
        assertNotNull(recyclingTips, "RecyclingTips object should not be null.");
        assertTrue(recyclingTips.tips().isEmpty(), "Tips list should be empty.");
    }

    @Test
    void testRecyclingTipsCreationWithNullList() {
        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new RecyclingTips(null);
        });
        assertEquals("Recycling tips list cannot be null", exception.getMessage(), "Exception message should match.");
    }

    @Test
    void testToStringMethod() {
        RecyclingTip tip = new RecyclingTip(1, "Separate recyclables", "General Waste");
        RecyclingTips recyclingTips = new RecyclingTips(List.of(tip));

        String result = recyclingTips.toString();

        // Assert
        assertTrue(result.contains("Separate recyclables"), "toString should include the tip content.");
        assertTrue(result.contains("General Waste"), "toString should include the category.");
    }
}