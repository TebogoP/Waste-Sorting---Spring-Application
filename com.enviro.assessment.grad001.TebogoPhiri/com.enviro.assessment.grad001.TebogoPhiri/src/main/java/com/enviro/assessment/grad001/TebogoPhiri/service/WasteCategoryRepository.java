    package com.enviro.assessment.grad001.TebogoPhiri.service;

    import com.enviro.assessment.grad001.TebogoPhiri.model.WasteCategory;
    import jakarta.annotation.PostConstruct;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Repository;
    import org.springframework.web.server.ResponseStatusException;

    import java.io.File;
    import java.io.IOException;
    import java.util.List;

    /**
     * @Repository annotation marks this class as a Data Access Object (DAO).
     * This version uses a JSON file as the data storage for WasteCategory objects
     */
    @Repository
    public class WasteCategoryRepository {

        private static final String FILE_PATH = "waste_categories.json";
        JSONFileServices jfs = new JSONFileServices();

        /**
         * Retrieve all WasteCategory objects from the JSON file.
         * @return List of all WasteCategory objects.
         */
        public List<WasteCategory> getAllCategories() {
            try {
                return jfs.readFromFile(FILE_PATH, WasteCategory.class);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read from JSON file", e);
            }
        }

        /**
         * Find a WasteCategory by its ID.
         *
         * @param id The ID of the WasteCategory to find.
         * @return The WasteCategory object if found.
         * @throws ResponseStatusException if the category is not found.
         */
        public WasteCategory findById(Integer id) {
            List<WasteCategory> categories = getAllCategories();
            for (WasteCategory category : categories) {
                if (category.getId().equals(id)) {
                    return category;
                }
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WasteCategory with ID " + id + " not found");
        }

        /**
         * Create a new WasteCategory and save it to the JSON file.
         * @param category The WasteCategory to create.
         */
        public void create(WasteCategory category) {
            if (category.getId() == null || category.getName() == null || category.getDescription() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid WasteCategory: ID, Name, and Description must not be null");
            }

            List<WasteCategory> categories = getAllCategories();
            categories.add(category);

            try {
                jfs.saveToFile(categories,FILE_PATH);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save WasteCategory to JSON file", e);
            }
        }

        /**
         * Update an existing WasteCategory by its ID.
         * @param updatedCategory The updated WasteCategory data.
         * @param id              The ID of the WasteCategory to update.
         */
        public void update(WasteCategory updatedCategory, Integer id) {
            List<WasteCategory> categories = getAllCategories();
            boolean found = false;

            for (int i = 0; i < categories.size(); i++) {
                WasteCategory category = categories.get(i);
                if (category.getId().equals(id)) {
                    updatedCategory.setId(id); // Ensure the ID remains the same
                    categories.set(i, updatedCategory);
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WasteCategory with ID " + id + " not found");
            }

            try {
                jfs.saveToFile(categories,FILE_PATH);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update WasteCategory in JSON file", e);
            }
        }

        /**
         * Delete a WasteCategory by its ID.
         * @param id The ID of the WasteCategory to delete.
         */
        public void delete(Integer id) {
            List<WasteCategory> categories = getAllCategories();
            boolean found = false;

            for (int i = 0; i < categories.size(); i++) {
                WasteCategory category = categories.get(i);
                if (category.getId().equals(id)) {
                    categories.remove(i);
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WasteCategory with ID " + id + " not found");
            }

            try {
                jfs.saveToFile(categories,FILE_PATH);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete WasteCategory from JSON file", e);
            }
        }

        /**
         * Initialize the JSON file with sample data if it doesn't exist.
         */
        @PostConstruct
        private void init() {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                List<WasteCategory> sampleData = List.of(
                        new WasteCategory(1, "Plastic Waste", "Non-biodegradable synthetic materials."),
                        new WasteCategory(2, "Organic Waste", "Biodegradable waste from plants and animals."),
                        new WasteCategory(3, "Electronic Waste", "Old or discarded electronic devices such as computers and phones.")
                );
                try {
                    jfs.saveToFile(sampleData,FILE_PATH);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to initialize JSON file", e);
                }
            }
        }

    }
