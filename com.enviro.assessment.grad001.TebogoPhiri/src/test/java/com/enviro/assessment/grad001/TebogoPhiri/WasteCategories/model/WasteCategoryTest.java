package com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.model;

import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.model.WasteCategory;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WasteCategoryTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidWasteCategoryCreation() {
        // Positive Test Case
        WasteCategory category = new WasteCategory(1, "Organic Waste",
                "Biodegradable waste originating from plants, animals, or other organic materials.");

        Set<ConstraintViolation<WasteCategory>> violations = validator.validate(category);

        // Assert no violations
        assertTrue(violations.isEmpty(), "Valid WasteCategory should have no validation errors.");
        assertEquals(1, category.getId());
        assertEquals("Organic Waste", category.getName());
        assertEquals("Biodegradable waste originating from plants, animals, or other organic materials.", category.getDescription());
    }

    @Test
    void testValidWasteCategoryElectronicWaste() {
        // Positive Test Case
        WasteCategory category = new WasteCategory(2, "Electronic Waste (E-Waste)",
                "Old or discarded electronic devices such as computers and mobile phones.");

        Set<ConstraintViolation<WasteCategory>> violations = validator.validate(category);

        // Assert no violations
        assertTrue(violations.isEmpty(), "Valid WasteCategory should have no validation errors.");
        assertEquals(2, category.getId());
        assertEquals("Electronic Waste (E-Waste)", category.getName());
        assertEquals("Old or discarded electronic devices such as computers and mobile phones.", category.getDescription());
    }

    @Test
    void testWasteCategoryWithMissingName() {
        // Negative Test Case: Name is null
        WasteCategory category = new WasteCategory(3, null,
                "Waste generated during the extraction of minerals and metals.");

        Set<ConstraintViolation<WasteCategory>> violations = validator.validate(category);

        // Assert validation errors
        boolean hasNameError = false;
        for (ConstraintViolation<WasteCategory> violation : violations) {
            if (violation.getMessage().equals("Name cannot be blank")) {
                hasNameError = true;
                break;
            }
        }

        assertFalse(violations.isEmpty(), "WasteCategory with null name should have validation errors.");
        assertTrue(hasNameError, "Expected validation error for missing name.");
        assertEquals(3, category.getId());
        assertEquals("Waste generated during the extraction of minerals and metals.", category.getDescription());
    }

    @Test
    void testWasteCategoryWithEmptyDescription() {
        // Negative Test Case: Description is empty
        WasteCategory category = new WasteCategory(4, "Metal Waste", "");

        Set<ConstraintViolation<WasteCategory>> violations = validator.validate(category);

        // Assert validation errors
        boolean hasDescriptionError = false;
        for (ConstraintViolation<WasteCategory> violation : violations) {
            if (violation.getMessage().equals("Description cannot be blank")) {
                hasDescriptionError = true;
                break;
            }
        }

        assertFalse(violations.isEmpty(), "WasteCategory with empty description should have validation errors.");
        assertTrue(hasDescriptionError, "Expected validation error for empty description.");
        assertEquals(4, category.getId());
        assertEquals("Metal Waste", category.getName());
    }

    @Test
    void testWasteCategoryWithNegativeId() {
        // Negative Test Case: ID is negative
        WasteCategory category = new WasteCategory(-5, "Plastic Waste",
                "Non-biodegradable waste made from synthetic polymers like plastic bottles and bags.");

        // Validate the category
        Set<ConstraintViolation<WasteCategory>> violations = validator.validate(category);

        // Assert validation error for negative ID
        boolean hasIdError = false;
        for (ConstraintViolation<WasteCategory> violation : violations) {
            if (violation.getMessage().equals("ID should not be negative")) {
                hasIdError = true;
                break;
            }
        }

        assertFalse(violations.isEmpty(), "WasteCategory with a negative ID should have validation errors.");
        assertTrue(hasIdError, "Expected validation error for negative ID.");
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
