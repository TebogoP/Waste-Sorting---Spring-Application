package com.enviro.assessment.grad001.TebogoPhiri;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class WasteSortingApplicationTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture the application's output
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        // Restore the original System.out
        System.setOut(originalOut);
    }

//    @Test
//    void testApplicationStartupLogs() {
//        // Act: Run the application
//        WasteSortingApplication.main(new String[]{});
//
//        // Capture the application's output
//        String actualOutput = outputStream.toString().trim();
//
//        // Assert: Check if the log message is present
//        assertTrue(
//                actualOutput.contains("Waste Sorting Application started successfully"),
//                "Expected log message not found in output."
//        );
//    }
}
