package com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecyclingTipTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        // Initialize the validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidRecyclingTipCreation() {
        // Positive test case: Valid data
        RecyclingTip recyclingTip = new RecyclingTip(1, "Separate glass bottles", "Glass");
        Set<ConstraintViolation<RecyclingTip>> violations = validator.validate(recyclingTip);

        assertTrue(violations.isEmpty(), "No violations should occur for valid RecyclingTip");
        assertEquals(1, recyclingTip.getId());
        assertEquals("Separate glass bottles", recyclingTip.getTip());
        assertEquals("Glass", recyclingTip.getCategory());
    }

    @Test
    void testRecyclingTipWithNullId() {
        // Negative test case: Null ID
        RecyclingTip recyclingTip = new RecyclingTip(null, "Rinse plastic bottles", "Plastic");
        Set<ConstraintViolation<RecyclingTip>> violations = validator.validate(recyclingTip);

        assertFalse(violations.isEmpty(), "Violations should occur when ID is null");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("ID cannot be null")));
    }

    @Test
    void testRecyclingTipWithNegativeId() {
        // Negative test case: Negative ID
        RecyclingTip recyclingTip = new RecyclingTip(-1, "Rinse plastic bottles", "Plastic");
        Set<ConstraintViolation<RecyclingTip>> violations = validator.validate(recyclingTip);

        assertFalse(violations.isEmpty(), "Violations should occur when ID is negative");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("ID should not be negative")));
    }

    @Test
    void testRecyclingTipWithBlankTip() {
        // Negative test case: Blank tip
        RecyclingTip recyclingTip = new RecyclingTip(2, "", "Paper");
        Set<ConstraintViolation<RecyclingTip>> violations = validator.validate(recyclingTip);

        // Assert
        assertFalse(violations.isEmpty(), "Validation errors should occur when wasteType is blank.");
        assertEquals("Tip cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testRecyclingTipWithNullCategory() {
        // Negative test case: Null category
        RecyclingTip recyclingTip = new RecyclingTip(3, "Recycle paper properly", null);
        Set<ConstraintViolation<RecyclingTip>> violations = validator.validate(recyclingTip);

        assertFalse(violations.isEmpty(), "Validation errors should occur when guideline is blank.");
        assertEquals("Category cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testRecyclingTipWithExcessiveLength() {
        // Negative test case: Category exceeds 255 characters
        String longCategory = "a".repeat(256);
        RecyclingTip recyclingTip = new RecyclingTip(4, "Avoid contaminating bins", longCategory);
        Set<ConstraintViolation<RecyclingTip>> violations = validator.validate(recyclingTip);

        assertFalse(violations.isEmpty(), "Violations should occur when category exceeds 255 characters");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Category cannot exceed 255 characters")));
    }

    @Test
    void testToStringMethod() {
        // Positive test case: Test toString output
        RecyclingTip recyclingTip = new RecyclingTip(5, "Clean aluminum cans", "Metal");
        String expectedOutput = "{\"id\": 5, \"tip\": \"Clean aluminum cans\", \"category\": \"Metal\"}";

        assertEquals(expectedOutput, recyclingTip.toString(), "toString method should return the correct JSON representation");
    }
}
