package com.enviro.assessment.grad001.TebogoPhiri.service;

import com.enviro.assessment.grad001.TebogoPhiri.model.WasteCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class WasteCategoryRepositoryTest {

    private WasteCategoryRepository wasteCategoryRepository;

    private JSONFileServices mockJSONFileServices;

    private final String FILE_PATH = "waste_categories.json";

    @BeforeEach
    void setUp() {
        // Set up the mocked JSONFileServices and inject it manually
        mockJSONFileServices = Mockito.mock(JSONFileServices.class);
        wasteCategoryRepository = new WasteCategoryRepository();
        wasteCategoryRepository.jfs = mockJSONFileServices;
    }

    @Test
    void testGetAllCategoriesSuccess() throws IOException {
        // Arrange an immutable list
        List<WasteCategory> categories = List.of(
                new WasteCategory(1, "Plastic Waste", "Non-biodegradable synthetic materials."),
                new WasteCategory(2, "Organic Waste", "Biodegradable waste from plants and animals.")
        );
        when(mockJSONFileServices.readFromFile(FILE_PATH, WasteCategory.class)).thenReturn(categories);

        List<WasteCategory> result = wasteCategoryRepository.getAllCategories();

        // Asserts
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Plastic Waste", result.get(0).getName());
    }

    @Test
    void testFindByIdSuccess() throws IOException {
        // Arrange an immutable list
        List<WasteCategory> categories = List.of(
                new WasteCategory(1, "Plastic Waste", "Non-biodegradable synthetic materials."),
                new WasteCategory(2, "Organic Waste", "Biodegradable waste from plants and animals.")
        );
        when(mockJSONFileServices.readFromFile(FILE_PATH, WasteCategory.class)).thenReturn(categories);

        WasteCategory category = wasteCategoryRepository.findById(1);

        // Asserts
        assertNotNull(category);
        assertEquals(1, category.getId());
        assertEquals("Plastic Waste", category.getName());
    }

    @Test
    void testFindByIdNotFound() throws IOException {
        // Arrange an immutable list
        List<WasteCategory> categories = new ArrayList<>();
        when(mockJSONFileServices.readFromFile(FILE_PATH, WasteCategory.class)).thenReturn(categories);

        // Asserts
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            wasteCategoryRepository.findById(999);
        });
        assertEquals("404 NOT_FOUND \"WasteCategory with ID 999 not found\"", exception.getMessage());
    }

    @Test
    void testCreateSuccess() throws IOException {

        List<WasteCategory> categories = new ArrayList<>();
        when(mockJSONFileServices.readFromFile(FILE_PATH, WasteCategory.class)).thenReturn(categories);

        WasteCategory newCategory = new WasteCategory(3, "Glass Waste", "Recyclable glass materials.");

        wasteCategoryRepository.create(newCategory);

        // Asserts
        categories.add(newCategory); // Expected result after creation
        verify(mockJSONFileServices, times(1)).saveToFile(categories, FILE_PATH);
    }
    @Test
    void testCreateCategoryAlreadyExists() throws IOException {
        // Arrange an immutable list
        List<WasteCategory> categories = List.of(
                new WasteCategory(1, "Plastic Waste", "Non-biodegradable synthetic materials."),
                new WasteCategory(3, "Glass Waste", "Recyclable glass materials.")
        );
        when(mockJSONFileServices.readFromFile(FILE_PATH, WasteCategory.class)).thenReturn(categories);

        WasteCategory duplicateCategory = new WasteCategory(3, "Duplicate Glass Waste", "Duplicate recyclable glass materials.");

        //Asserts
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            wasteCategoryRepository.create(duplicateCategory);
        });

        assertEquals("302 FOUND \"WasteCategory with ID 3 already exist\"", exception.getMessage());
        verify(mockJSONFileServices, never()).saveToFile(anyList(), eq(FILE_PATH));
    }


    @Test
    void testCreateInvalidCategory() {

        WasteCategory invalidCategory = new WasteCategory(null, null, "Missing name and ID.");

        //Asserts
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            wasteCategoryRepository.create(invalidCategory);
        });
        assertEquals("400 BAD_REQUEST \"Invalid WasteCategory: ID, Name, and Description must not be null\"", exception.getMessage());
    }

    @Test
    void testUpdateSuccess() throws IOException {

        List<WasteCategory> categories = new ArrayList<>();
        categories.add(new WasteCategory(1, "Old Waste", "Old description."));
        when(mockJSONFileServices.readFromFile(FILE_PATH, WasteCategory.class)).thenReturn(categories);

        WasteCategory updatedCategory = new WasteCategory(null, "Updated Waste", "Updated description.");

        wasteCategoryRepository.update(updatedCategory, 1);

        // Asserts
        updatedCategory.setId(1);
        categories.set(0, updatedCategory); // Expected result after update
        verify(mockJSONFileServices, times(1)).saveToFile(categories, FILE_PATH);
    }

    @Test
    void testUpdateNotFound() throws IOException {

        List<WasteCategory> categories = new ArrayList<>();
        when(mockJSONFileServices.readFromFile(FILE_PATH, WasteCategory.class)).thenReturn(categories);

        WasteCategory updatedCategory = new WasteCategory(null, "Updated Waste", "Updated description.");

        //Asserts
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            wasteCategoryRepository.update(updatedCategory, 999);
        });
        assertEquals("404 NOT_FOUND \"WasteCategory with ID 999 not found\"", exception.getMessage());
    }

    @Test
    void testDeleteSuccess() throws IOException {

        List<WasteCategory> categories = new ArrayList<>();
        categories.add(new WasteCategory(1, "Waste to Remove", "Description to remove."));
        when(mockJSONFileServices.readFromFile(FILE_PATH, WasteCategory.class)).thenReturn(categories);

        wasteCategoryRepository.delete(1);

        // Asserts
        categories.removeIf(category -> category.getId().equals(1)); // Expected result after deletion
        verify(mockJSONFileServices, times(1)).saveToFile(categories, FILE_PATH);
    }

    @Test
    void testDeleteNotFound() throws IOException {

        List<WasteCategory> categories = new ArrayList<>();
        when(mockJSONFileServices.readFromFile(FILE_PATH, WasteCategory.class)).thenReturn(categories);

        //Asserts
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            wasteCategoryRepository.delete(999);
        });
        assertEquals("404 NOT_FOUND \"WasteCategory with ID 999 not found\"", exception.getMessage());
    }
    @Test
    void testGetAllCategoriesEmptyFile() throws IOException {
        // Arrange: Simulate empty file content
        when(mockJSONFileServices.readFromFile(FILE_PATH, WasteCategory.class)).thenReturn(new ArrayList<>());

        List<WasteCategory> result = wasteCategoryRepository.getAllCategories();

        // Asserts
        assertNotNull(result, "Result should not be null.");
        assertTrue(result.isEmpty(), "Result should be an empty list for an empty file.");
    }

}
