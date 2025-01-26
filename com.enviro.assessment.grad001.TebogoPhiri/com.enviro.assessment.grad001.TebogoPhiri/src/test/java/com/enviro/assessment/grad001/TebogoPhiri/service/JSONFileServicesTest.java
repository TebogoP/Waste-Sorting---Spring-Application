package com.enviro.assessment.grad001.TebogoPhiri.service;

import com.enviro.assessment.grad001.TebogoPhiri.model.WasteCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JSONFileServicesTest {

    @Autowired
    private JSONFileServices jsonFileServices; // Inject the JSONFileServices bean

    private final String testFilePath = "test-waste-categories.json";

    @AfterEach
    void cleanUp() {
        // Delete the test file after each test
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testSaveToFileSuccess() throws IOException {
        // Positive Test: Save a valid list to a JSON file
        List<WasteCategory> categories = new ArrayList<>();
        categories.add(new WasteCategory(1, "Organic Waste", "Biodegradable waste from plants and animals."));
        categories.add(new WasteCategory(2, "Plastic Waste", "Non-biodegradable synthetic materials."));

        jsonFileServices.saveToFile(categories, testFilePath);

        // Assert
        File file = new File(testFilePath);
        assertTrue(file.exists(), "File should be created successfully.");
        assertTrue(file.length() > 0, "File should not be empty.");
    }

    @Test
    void testReadFromFileSuccess() throws IOException {
        // Positive Test: Read a valid JSON file
        List<WasteCategory> categories = new ArrayList<>();
        categories.add(new WasteCategory(1, "Metal Waste", "Discarded metal materials."));
        categories.add(new WasteCategory(2, "Glass Waste", "Broken glass and bottles."));

        // Save the list to a file
        jsonFileServices.saveToFile(categories, testFilePath);

        List<WasteCategory> readCategories = jsonFileServices.readFromFile(testFilePath, WasteCategory.class);

        // Asserts
        assertNotNull(readCategories, "Read categories should not be null.");
        assertEquals(2, readCategories.size(), "There should be 2 categories.");
        assertEquals("Metal Waste", readCategories.get(0).getName(), "First category name should match.");
    }

    @Test
    void testReadFromFileFileDoesNotExist() throws IOException {
        // Negative Test: Read from a non-existent file
        String nonExistentFilePath = "non-existent-file.json";

        // Reading the list to a file
        List<WasteCategory> readCategories = jsonFileServices.readFromFile(nonExistentFilePath, WasteCategory.class);

        // Asserts
        assertNotNull(readCategories, "Read categories should not be null.");
        assertTrue(readCategories.isEmpty(), "Read categories should be empty for a non-existent file.");
    }

    @Test
    void testSaveToFileNullList() {
        // Negative Test: Save a null list to a file
        List<WasteCategory> nullList = null;

        //Testing exception handling
        assertThrows(NullPointerException.class, () -> jsonFileServices.saveToFile(nullList, testFilePath),
                "Saving a null list should throw NullPointerException.");
    }

    @Test
    void testReadFromFileInvalidJsonFormat() throws IOException {
        // Negative Test: Read a file with invalid JSON format
        File file = new File(testFilePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        // Write invalid JSON content to the file
        String invalidJson = "{invalid-json}";
        Files.writeString(file.toPath(), invalidJson);

        //Testing exception handling
        assertThrows(IOException.class, () -> jsonFileServices.readFromFile(testFilePath, WasteCategory.class),
                "Reading invalid JSON content should throw IOException.");
    }

    @Test
    void testSaveToFileEmptyList() throws IOException {
        // Edge Case: Save an empty list to a file
        List<WasteCategory> emptyList = new ArrayList<>();

        //Save the list to a file
        jsonFileServices.saveToFile(emptyList, testFilePath);

        // Asserts
        File file = new File(testFilePath);
        assertTrue(file.exists(), "File should be created successfully.");
        assertTrue(file.length() > 0, "File should not be empty, as it should contain valid JSON.");
    }
    @Test
    void testReadFromFileEmptyFile() throws IOException {
        // Arrange: Create an empty file
        File file = new File(testFilePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        List<WasteCategory> result = jsonFileServices.readFromFile(testFilePath, WasteCategory.class);

        // Asserts
        assertNotNull(result, "Result should not be null.");
        assertTrue(result.isEmpty(), "Result should be an empty list for an empty file.");
    }
}
