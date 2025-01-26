package com.enviro.assessment.grad001.TebogoPhiri;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WasteSortingApplicationTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture print stream
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        // Restore original output stream
        System.setOut(originalOut);
    }

    @Test
    void testApplicationStartup() throws IOException {
        // Arrange
        /* Simulate any necessary arguments for the main method*/
        //String[] args = {};

        WasteSortingApplication.main(new String[]{});

        // Asserts
        String actualOutput = outputStream.toString().trim();
        assertNotNull(actualOutput, "Output should not be null");
        assertFalse(actualOutput.isEmpty(), "Output should not be empty");
        assertTrue(actualOutput.contains("Waste Sorting Application started successfully"));
    }


}