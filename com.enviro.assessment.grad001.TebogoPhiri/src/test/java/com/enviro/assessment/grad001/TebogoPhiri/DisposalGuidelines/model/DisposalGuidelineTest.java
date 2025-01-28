package com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DisposalGuidelineTest {

    private final Validator validator;

    public DisposalGuidelineTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testValidDisposalGuideline() {
        // Arrange
        DisposalGuideline guideline = new DisposalGuideline(1, "Paper Waste", "Flatten cardboard boxes before disposal.");

        // Act
        Set<ConstraintViolation<DisposalGuideline>> violations = validator.validate(guideline);

        // Assert
        assertTrue(violations.isEmpty(), "There should be no validation errors for a valid DisposalGuideline.");
    }

    @Test
    void testDisposalGuidelineNullId() {
        DisposalGuideline guideline = new DisposalGuideline(null, "Plastic Waste", "Rinse plastic containers before disposal.");

        Set<ConstraintViolation<DisposalGuideline>> violations = validator.validate(guideline);

        // Assert
        assertFalse(violations.isEmpty(), "Validation errors should occur when ID is null.");
        assertEquals("ID cannot be null", violations.iterator().next().getMessage());
    }

    @Test
    void testDisposalGuidelineNegativeId() {
        DisposalGuideline guideline = new DisposalGuideline(-1, "Metal Waste", "Recycle metal cans separately.");

        Set<ConstraintViolation<DisposalGuideline>> violations = validator.validate(guideline);

        // Assert
        assertFalse(violations.isEmpty(), "Validation errors should occur when ID is negative.");
        assertEquals("ID should not be negative", violations.iterator().next().getMessage());
    }

    @Test
    void testDisposalGuidelineBlankWasteType() {
        DisposalGuideline guideline = new DisposalGuideline(2, "", "Dispose of e-waste at authorized centers.");

        Set<ConstraintViolation<DisposalGuideline>> violations = validator.validate(guideline);

        // Assert
        assertFalse(violations.isEmpty(), "Validation errors should occur when wasteType is blank.");
        assertEquals("wasteType cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testDisposalGuidelineBlankGuideline() {
        DisposalGuideline guideline = new DisposalGuideline(3, "Hazardous Waste", "");

        Set<ConstraintViolation<DisposalGuideline>> violations = validator.validate(guideline);

        // Assert
        assertFalse(violations.isEmpty(), "Validation errors should occur when guideline is blank.");
        assertEquals("Guideline cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testDisposalGuidelineWasteTypeExceedsMaxLength() {

        String longWasteType = "WasteType".repeat(30); // Exceeding 255 characters
        DisposalGuideline guideline = new DisposalGuideline(4, longWasteType, "Follow appropriate disposal methods.");

        Set<ConstraintViolation<DisposalGuideline>> violations = validator.validate(guideline);

        // Assert
        assertFalse(violations.isEmpty(), "Validation errors should occur when wasteType exceeds max length.");
        assertEquals("wasteType cannot exceed 255 characters", violations.iterator().next().getMessage());
    }

    @Test
    void testToStringMethod() {
        DisposalGuideline guideline = new DisposalGuideline(5, "Glass Waste", "Recycle glass bottles by color.");

        String expectedString = "{\"id\": 5, \"wasteType\": \"Glass Waste\",\"guideline\": \"Recycle glass bottles by color.\"}";

        // Asser
        assertEquals(expectedString, guideline.toString().trim(), "toString() method should return the correct JSON-like format.");
    }
}
