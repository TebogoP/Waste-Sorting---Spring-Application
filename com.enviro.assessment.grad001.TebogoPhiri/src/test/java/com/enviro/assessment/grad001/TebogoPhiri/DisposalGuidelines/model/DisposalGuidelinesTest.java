package com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.model;

import com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.model.RecyclingTips;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class DisposalGuidelinesTest {
    @Test
    void testDisposalGuidelinesCreationWithValidData() {
        DisposalGuideline guideline1 = new DisposalGuideline(1, "Paper Waste", "Flatten cardboard boxes before disposal.");
        DisposalGuideline guideline2 = new DisposalGuideline(2, "Plastic Waste", "Rinse containers before recycling.");
        List<DisposalGuideline> guidelineList = List.of(guideline1, guideline2);

        DisposalGuidelines disposalGuidelines = new DisposalGuidelines(guidelineList);

        // Assert
        assertNotNull(disposalGuidelines, "DisposalGuidelines object should not be null.");
        assertEquals(2, disposalGuidelines.disposal().size(), "Guidelines list should contain 2 items.");
        assertEquals("Paper Waste", disposalGuidelines.disposal().get(0).getWasteType(), "First guideline type should match.");
    }

    @Test
    void testDisposalGuidelinesCreationWithEmptyList() {
        List<DisposalGuideline> emptyGuidelines = List.of();

        DisposalGuidelines disposalGuidelines = new DisposalGuidelines(emptyGuidelines);

        // Assert
        assertNotNull(disposalGuidelines, "DisposalGuidelines object should not be null.");
        assertTrue(disposalGuidelines.disposal().isEmpty(), "Guidelines list should be empty.");
    }

    @Test
    void testDisposalGuidelinesCreationWithNullList() {
        //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new DisposalGuidelines(null);
        });
        assertEquals("Disposal guidelines list cannot be null", exception.getMessage(), "Exception message should match.");
    }

    @Test
    void testToStringMethod() {
        DisposalGuideline guideline = new DisposalGuideline(1, "Hazardous Waste", "Dispose of chemicals at authorized facilities.");
        DisposalGuidelines disposalGuidelines = new DisposalGuidelines(List.of(guideline));

        String result = disposalGuidelines.toString();

        // Assert
        assertTrue(result.contains("Hazardous Waste"), "toString should include the waste type.");
        assertTrue(result.contains("Dispose of chemicals at authorized facilities."), "toString should include the guideline description.");
    }
}
