package com.enviro.assessment.grad001.TebogoPhiri.service;

import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.model.WasteCategory;
import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.service.WasteCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class WasteCategoryRepositoryTest {
    @Autowired
    private WasteCategoryRepository repository;

    @Test
    void getAllCategories_ShouldReturnAllCategories() {
        // Create test data
        WasteCategory category1 = new WasteCategory(1, "Test Category 1", "Description 1");
        WasteCategory category2 = new WasteCategory(2, "Test Category 2", "Description 2");
        repository.saveAll(List.of(category1, category2));

        // Test
        List<WasteCategory> categories = repository.getAllCategories();

        // Assert
        assertThat(categories).hasSize(2);
        assertThat(categories).extracting(WasteCategory::getName)
                .containsExactlyInAnyOrder("Test Category 1", "Test Category 2");
    }

    @Test
    void findById_ShouldReturnCategory_WhenExists() {
        // Create test data
        WasteCategory category = new WasteCategory(1, "Test Category", "Description");
        repository.create(category);

        // Test
        WasteCategory found = repository.findById(1);

        // Assert
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test Category");
    }

    @Test
    void findById_ShouldThrowException_WhenNotExists() {
        assertThrows(RuntimeException.class, () -> repository.findById(999));
    }

    @Test
    void create_ShouldAddNewCategory() {
        // Create test data
        WasteCategory category = new WasteCategory(1, "New Category", "Description");

        // Test
        repository.create(category);

        // Assert
        WasteCategory found = repository.findById(1);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("New Category");
    }

    @Test
    void update_ShouldModifyExistingCategory() {
        // Create test data
        WasteCategory original = new WasteCategory(1, "Original", "Description");
        repository.create(original);

        // Test
        WasteCategory updated = new WasteCategory(1, "Updated", "New Description");
        repository.update(updated, 1);

        // Assert
        WasteCategory found = repository.findById(1);
        assertThat(found.getName()).isEqualTo("Updated");
        assertThat(found.getDescription()).isEqualTo("New Description");
    }

    @Test
    void delete_ShouldRemoveCategory() {
        // Create test data
        WasteCategory category = new WasteCategory(1, "To Delete", "Description");
        repository.create(category);

        // Test
        repository.delete(1);

        // Assert
        assertThrows(RuntimeException.class, () -> repository.findById(1));
    }

    @Test
    void count_ShouldReturnNumberOfCategories() {
        // Create test data
        WasteCategory category1 = new WasteCategory(1, "Category 1", "Description 1");
        WasteCategory category2 = new WasteCategory(2, "Category 2", "Description 2");
        repository.saveAll(List.of(category1, category2));

        // Test
        int count = repository.count();

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    void saveAll_ShouldAddMultipleCategories() {
        // Create test data
        List<WasteCategory> categories = List.of(
                new WasteCategory(1, "Category 1", "Description 1"),
                new WasteCategory(2, "Category 2", "Description 2")
        );

        // Test
        repository.saveAll(categories);

        // Assert
        List<WasteCategory> found = repository.getAllCategories();
        assertThat(found).hasSize(2);
        assertThat(found).extracting(WasteCategory::getId)
                .containsExactlyInAnyOrder(1, 2);
    }

    @BeforeEach
    void setUp() {
        // Clean up before each test
        List<WasteCategory> existingCategories = repository.getAllCategories();
        for (WasteCategory category : existingCategories) {
            repository.delete(category.getId());
        }
    }
}