    package com.enviro.assessment.grad001.TebogoPhiri.service;

    import com.enviro.assessment.grad001.TebogoPhiri.model.WasteCategory;
    import jakarta.annotation.PostConstruct;
    import netscape.javascript.JSObject;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Repository;
    import org.springframework.web.server.ResponseStatusException;

    import java.io.File;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;

    /**
     * @Repository annotation marks this class as a Data Access Object (DAO).
     * This version uses a JSON file as the data storage for WasteCategory objects
     */
    @Repository
    public class WasteCategoryRepository {

        private static final String FILE_PATH = "waste_categories.json";
        private static final Logger log = LoggerFactory.getLogger(WasteCategoryRepository.class);
        JSONFileServices jfs = new JSONFileServices();

        /**
         * Retrieve all WasteCategory objects from the JSON file.
         * @return List of all WasteCategory objects.
         */
        public List<WasteCategory> getAllCategories() {
            try {
                List<WasteCategory> categories = jfs.readFromFile(FILE_PATH, WasteCategory.class);

                if (categories == null || categories.isEmpty()) {
                    return new ArrayList<>(); // Return an empty list if no data is present
                }
                return categories;
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
        public WasteCategory    findById(Integer id) {
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
         * @param newCategory The WasteCategory to create.
         */
        public void create(WasteCategory newCategory) {
            if (newCategory.getId() == null || newCategory.getName() == null || newCategory.getDescription() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid WasteCategory: ID, Name, and Description must not be null");
            }
            List<WasteCategory> categories = getAllCategories();
            WasteCategory categoryFound = null;
            try{
                categoryFound = findById(newCategory.getId());
            }
            catch (ResponseStatusException e) {
                categories.add(newCategory);
            }
            if (categoryFound != null) {
                throw new ResponseStatusException(HttpStatus.FOUND, "WasteCategory with ID " + categoryFound.getId() + " already exist");
            }


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
            List<WasteCategory> newCategories = new ArrayList<>();
                // Try to find and remove the category
                try {
                    WasteCategory existingWasteCategory = findById(id);
                    for (WasteCategory category : categories) {
                        if (category.getId().equals(existingWasteCategory.getId())) {
                            updatedCategory.setId(id);
                            newCategories.add(updatedCategory);
                            // Directly remove the found category
                        }else {
                            newCategories.add(category);
                        }
                    }

            }
            catch (ResponseStatusException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WasteCategory with ID " + id + " not found");
            }

            try {
                jfs.saveToFile(newCategories,FILE_PATH);
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
            List<WasteCategory> newCategories = new ArrayList<>();

            // Try to find and remove the category
            try {
                WasteCategory existingWasteCategory = findById(id);
               for (WasteCategory category : categories) {
                   if (category.getId().equals(existingWasteCategory.getId())) {
                       continue; // Directly remove the found category
                   }
                   newCategories.add(category);
               }
                //categories.remove(existingWasteCategory); /
                System.out.println("WasteCategory with ID " + id + " has been removed.");
            } catch (ResponseStatusException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WasteCategory with ID " + id + " not found", e);
            }

            // Save updated list back to the file
            try {
                jfs.saveToFile(newCategories, FILE_PATH); // Overwrites the file with updated list
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete WasteCategory from JSON file", e);
            }
        }


        /**
         * Initialize the JSON file with sample data if it doesn't exist.
         */
        @PostConstruct
        private void init() throws IOException {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                System.out.println("File " + FILE_PATH + " does not exist");
                populate(jfs,file.getPath());}
            if (file.exists()){
                List<WasteCategory> categories = getAllCategories();
                // Return an empty list if the file content is empty or null
                if (categories == null || categories.isEmpty()) {
                    System.out.println("Content Empty");
                    populate(jfs,file.getPath());
                }


            }


        }
        private void populate(JSONFileServices jfs,String filePath){
            List<WasteCategory> sampleData = List.of(
                    new WasteCategory(1, "Plastic Waste", "Non-biodegradable synthetic materials."),
                    new WasteCategory(2, "Organic Waste", "Biodegradable waste from plants and animals."),
                    new WasteCategory(3, "Electronic Waste", "Old or discarded electronic devices such as computers and phones.")
            );
            try {
                jfs.saveToFile(sampleData,filePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize JSON file", e);
            }
        }
    }



