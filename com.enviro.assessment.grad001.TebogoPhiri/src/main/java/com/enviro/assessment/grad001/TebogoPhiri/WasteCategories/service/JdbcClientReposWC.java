package com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.service;

import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.model.WasteCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @Repository annotation marks this class as a Data Access Object (DAO).
 * This repository handles database operations for WasteCategory objects using JdbcClient.
 */
@Repository // Formally known as WasteCategoryRepository
public class JdbcClientReposWC {
    private static final Logger log = LoggerFactory.getLogger(JdbcClientReposWC.class);
    private final JdbcClient jdbcClient;

    public JdbcClientReposWC(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    /**
     * Retrieve all WasteCategory objects from the database.
     * @return List of all WasteCategory objects.
     */
    public List<WasteCategory> getAllCategories() {
        return jdbcClient.sql("SELECT * FROM waste_category")
                .query(WasteCategory.class)
                .list();
    }

    /**
     * Find a WasteCategory by its ID.
     * @param id The ID of the WasteCategory to find.
     * @return The WasteCategory object if found.
     * @throws RuntimeException if the category is not found.
     */
    public WasteCategory findById(Integer id) {
        try {
            return jdbcClient.sql("SELECT * FROM waste_category WHERE id = :id")
                    .param("id", id)
                    .query(WasteCategory.class)
                    .single();
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("WasteCategory not found with id: " + id);
        }
    }

    /**
     * Create a new WasteCategory in the database.
     * @param category The WasteCategory to create.
     * @throws IllegalStateException if creation fails.
     */
    public void create(WasteCategory category) {
        var created = jdbcClient.sql("INSERT INTO waste_category (id, name, description) VALUES (:id, :name, :description)")
                .param("id", category.getId())
                .param("name", category.getName())
                .param("description", category.getDescription())
                .update();

        Assert.state(created == 1, "Failed to create category: " + category.getName());
    }

    /**
     * Update an existing WasteCategory by its ID.
     * @param updateCategory The updated WasteCategory data.
     * @param id The ID of the WasteCategory to update.
     * @throws IllegalStateException if update fails.
     */
    public void update(WasteCategory updateCategory, Integer id) {
        var updated = jdbcClient.sql("UPDATE waste_category SET name = :name, description = :description WHERE id = :id")
                .param("name", updateCategory.getName())
                .param("description", updateCategory.getDescription())
                .param("id", id)
                .update();
        Assert.state(updated == 1, "Failed to update category to: " + updateCategory.getName());
    }

    /**
     * Delete a WasteCategory by its ID.
     * @param id The ID of the WasteCategory to delete.
     * @throws IllegalStateException if deletion fails.
     */
    public void delete(Integer id) {
        var deleted = jdbcClient.sql("DELETE FROM waste_category WHERE id = :id")
                .param("id", id)
                .update();
        Assert.state(deleted == 1, "Failed to delete category with id: " + id);
    }

    /**
     * Count the total number of WasteCategory entries in the database.
     * @return The total count of WasteCategory entries.
     */
    public int count() {
        return jdbcClient.sql("SELECT * FROM waste_category")
                .query()
                .listOfRows()
                .size();
    }

    /**
     * Save multiple WasteCategory objects to the database.
     * @param newCategories List of WasteCategory objects to save.
     */
    public void saveAll(List<WasteCategory> newCategories) {
        for (WasteCategory category : newCategories) {
            try {
                create(category);
            } catch (Exception e) {
                log.warn("Skipping duplicate category with ID {}: {}", category.getId(), e.getMessage());
            }
        }
    }
}