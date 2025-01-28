package com.enviro.assessment.grad001.TebogoPhiri.Generic.Service;

import com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.model.DisposalGuideline;
import com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.model.RecyclingTip;
import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.model.WasteCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

/**
 * @Repository annotation marks this class as a Data Access Object (DAO).
 * This repository handles database operations for generic entities using JdbcClient.
 */
@Repository
public class JdbcClientRepos {
    private static final Logger log = LoggerFactory.getLogger(JdbcClientRepos.class);
    private final JdbcClient jdbcClient;

    public JdbcClientRepos(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private final HashMap<Class<?>, String> tables = new HashMap<>() {{
        put(WasteCategory.class, "waste_category");
        put(RecyclingTip.class, "recycling_tip");
        put(DisposalGuideline.class, "disposal_guideline");
    }};

    private String getTableName(Class<?> clazz) {
        if (!tables.containsKey(clazz)) {
            throw new IllegalArgumentException("Table name not registered for class: " + clazz.getSimpleName());
        }
        return tables.get(clazz);
    }

    /**
     * Retrieve all objects from the database for a given entity class.
     *
     * @param clazz The entity class type.
     * @param <T>   The type of the entity.
     * @return List of all objects of the given type.
     */
    public <T> List<T> findAll(Class<T> clazz) {
        String tableName = getTableName(clazz);
        return jdbcClient.sql("SELECT * FROM " + tableName)
                .query(clazz)
                .list();
    }

    /**
     * Find an object by its ID for a given entity class.
     *
     * @param clazz The entity class type.
     * @param id    The ID of the object.
     * @param <T>   The type of the entity.
     * @return The object if found.
     */
    public <T> T findById(Class<T> clazz, Integer id) {
        String tableName = getTableName(clazz);
        try {
            return jdbcClient.sql("SELECT * FROM " + tableName + " WHERE id = :id")
                    .param("id", id)
                    .query(clazz)
                    .single();
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException(clazz.getSimpleName() + " not found with ID: " + id);
        }
    }

    /**
     * Create a new entity in the database.
     *
     * @param entity The entity object to create.
     * @param clazz  The entity class type.
     * @param <T>    The type of the entity.
     */
    public <T> void create(T entity, Class<T> clazz) {
        String tableName = getTableName(clazz);

        String insertQuery;
        switch (entity) {
            case WasteCategory wc -> {
                insertQuery = "INSERT INTO " + tableName + " (id, name, description) VALUES (:id, :name, :description)";
                jdbcClient.sql(insertQuery)
                        .param("id", wc.getId())
                        .param("name", wc.getName())
                        .param("description", wc.getDescription())
                        .update();
            }
            case RecyclingTip rt -> {
                insertQuery = "INSERT INTO " + tableName + " (id, tip, category) VALUES (:id, :tip, :category)";
                jdbcClient.sql(insertQuery)
                        .param("id", rt.getId())
                        .param("tip", rt.getTip())
                        .param("category", rt.getCategory())
                        .update();
            }
            case DisposalGuideline dg -> {
                insertQuery = "INSERT INTO " + tableName + " (id, wasteType, guideline) VALUES (:id, :wasteType, :guideline)";
                jdbcClient.sql(insertQuery)
                        .param("id", dg.getId())
                        .param("wasteType", dg.getWasteType())
                        .param("guideline", dg.getGuideline())
                        .update();
            }
            case null, default ->
                    throw new IllegalArgumentException("Unsupported entity type: " + clazz.getSimpleName());
        }

        log.info("Successfully created entity: {}", entity);
    }

    /**
     * Update an existing entity by its ID.
     *
     * @param entity The updated entity data.
     * @param id     The ID of the entity to update.
     * @param clazz  The entity class type.
     * @param <T>    The type of the entity.
     */
    public <T> void update(T entity, Integer id, Class<T> clazz) {
        String tableName = getTableName(clazz);

        String updateQuery;
        int updated;
        switch (entity) {
            case WasteCategory wc -> {
                updateQuery = "UPDATE " + tableName + " SET name = :name, description = :description WHERE id = :id";
                updated = jdbcClient.sql(updateQuery)
                        .param("name", wc.getName())
                        .param("description", wc.getDescription())
                        .param("id", id)
                        .update();
            }
            case RecyclingTip rt -> {
                updateQuery = "UPDATE " + tableName + " SET tip = :tip, category = :category WHERE id = :id";
                updated = jdbcClient.sql(updateQuery)
                        .param("tip", rt.getTip())
                        .param("category", rt.getCategory())
                        .param("id", id)
                        .update();
            }
            case DisposalGuideline dg -> {
                updateQuery = "UPDATE " + tableName + " SET wasteType = :wasteType, guideline = :guideline WHERE id = :id";
                updated = jdbcClient.sql(updateQuery)
                        .param("wasteType", dg.getWasteType())
                        .param("guideline", dg.getGuideline())
                        .param("id", id)
                        .update();
            }
            case null, default ->
                    throw new IllegalArgumentException("Unsupported entity type: " + clazz.getSimpleName());
        }

        Assert.state(updated == 1, "Failed to update entity with ID: " + id);
    }

    /**
     * Delete an entity by its ID.
     *
     * @param id    The ID of the entity to delete.
     * @param clazz The entity class type.
     * @param <T>   The type of the entity.
     */
    public <T> void delete(Integer id, Class<T> clazz) {
        String tableName = getTableName(clazz);
        int deleted = jdbcClient.sql("DELETE FROM " + tableName + " WHERE id = :id")
                .param("id", id)
                .update();
        Assert.state(deleted == 1, "Failed to delete entity with ID: " + id);
    }

    /**
     * Save a list of entities to the database.
     *
     * @param entities The list of entities to save.
     * @param clazz    The entity class type.
     * @param <T>      The type of the entity.
     */
    public <T> void saveAll(List<T> entities, Class<T> clazz) {
        for (T entity : entities) {
            try {
                create(entity, clazz);
            } catch (Exception e) {
                log.warn("Skipping duplicate entity: {}", entity, e);
            }
        }
    }
}
