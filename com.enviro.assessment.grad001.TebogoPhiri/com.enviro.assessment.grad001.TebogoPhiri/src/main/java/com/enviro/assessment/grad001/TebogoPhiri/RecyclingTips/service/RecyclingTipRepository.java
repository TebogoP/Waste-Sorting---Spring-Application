package com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.service;

import com.enviro.assessment.grad001.TebogoPhiri.Generic.JSONFileServices;
import com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.model.RecyclingTip;
import jakarta.annotation.PostConstruct;
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
 * This version uses a JSON file as the data storage for RecyclingTip objects
 */
@Repository
public class RecyclingTipRepository {

    private static final String FILE_PATH = "waste_recyclingTips.json";
    private static final Logger log = LoggerFactory.getLogger(RecyclingTipRepository.class);
//    JSONFileServices jfs = new JSONFileServices();
//
//    /**
//     * Retrieve all RecyclingTip objects from the JSON file.
//     * @return List of all RecyclingTip objects.
//     */
//    public List<RecyclingTip> getAllRecyclingTips() {
//        try {
//            List<RecyclingTip> recyclingTips = jfs.readFromFile(FILE_PATH, RecyclingTip.class);
//
//            if (recyclingTips == null || recyclingTips.isEmpty()) {
//                return new ArrayList<>(); // Return an empty list if no data is present
//            }
//            return recyclingTips;
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read from JSON file", e);
//        }
//    }
//
//
//    /**
//     * Find a RecyclingTip by its ID.
//     *
//     * @param id The ID of the RecyclingTip to find.
//     * @return The RecyclingTip object if found.
//     * @throws ResponseStatusException if the recyclingTip is not found.
//     */
//    public RecyclingTip findById(Integer id) {
//        List<RecyclingTip> recyclingTips = getAllRecyclingTips();
//        for (RecyclingTip recyclingTip : recyclingTips) {
//            if (recyclingTip.getId().equals(id)) {
//                return recyclingTip;
//            }
//        }
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RecyclingTip with ID " + id + " not found");
//    }
//
//    /**
//     * Create a new RecyclingTip and save it to the JSON file.
//     * @param newRecyclingTip The RecyclingTip to create.
//     */
//    public void create(RecyclingTip newRecyclingTip) {
//        if (newRecyclingTip.getId() == null || newRecyclingTip.getTip() == null || newRecyclingTip.getCategory() == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid RecyclingTip: ID, Guideline, and Waste Type must not be null");
//        }
//        List<RecyclingTip> recyclingTips = getAllRecyclingTips();
//        RecyclingTip recyclingTipFound = null;
//        try{
//            recyclingTipFound = findById(newRecyclingTip.getId());
//        }
//        catch (ResponseStatusException e) {
//            recyclingTips.add(newRecyclingTip);
//        }
//        if (recyclingTipFound != null) {
//            throw new ResponseStatusException(HttpStatus.FOUND, "RecyclingTip with ID " + recyclingTipFound.getId() + " already exist");
//        }
//
//
//        try {
//            jfs.saveToFile(recyclingTips,FILE_PATH);
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save RecyclingTip to JSON file", e);
//        }
//    }
//
//
//    /**
//     * Update an existing RecyclingTip by its ID.
//     * @param updatedTip The updated RecyclingTip data.
//     * @param id              The ID of the RecyclingTip to update.
//     */
//    public void update(RecyclingTip updatedTip, Integer id) {
//        List<RecyclingTip> recyclingTips = getAllRecyclingTips();
//        List<RecyclingTip> newRecyclingTips = new ArrayList<>();
//        // Try to find and remove the recyclingTip
//        try {
//            RecyclingTip existingRecyclingTip = findById(id);
//            for (RecyclingTip recyclingTip : recyclingTips) {
//                if (recyclingTip.getId().equals(existingRecyclingTip.getId())) {
//                    updatedTip.setId(id);
//                    newRecyclingTips.add(updatedTip);
//                    // Directly remove the found recyclingTip
//                }else {
//                    newRecyclingTips.add(recyclingTip);
//                }
//            }
//
//        }
//        catch (ResponseStatusException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RecyclingTip with ID " + id + " not found");
//        }
//
//        try {
//            jfs.saveToFile(newRecyclingTips,FILE_PATH);
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update RecyclingTip in JSON file", e);
//        }
//    }
//
//    /**
//     * Delete a RecyclingTip by its ID.
//     * @param id The ID of the RecyclingTip to delete.
//     */
//    public void delete(Integer id) {
//        List<RecyclingTip> recyclingTips = getAllRecyclingTips();
//        List<RecyclingTip> newRecyclingTips = new ArrayList<>();
//
//        // Try to find and remove the recyclingTip
//        try {
//            RecyclingTip existingRecyclingTip = findById(id);
//            for (RecyclingTip recyclingTip : recyclingTips) {
//                if (recyclingTip.getId().equals(existingRecyclingTip.getId())) {
//                    continue; // Directly remove the found recyclingTip
//                }
//                newRecyclingTips.add(recyclingTip);
//            }
//            //recyclingTips.remove(existingRecyclingTip); /
//            System.out.println("RecyclingTip with ID " + id + " has been removed.");
//        } catch (ResponseStatusException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RecyclingTip with ID " + id + " not found", e);
//        }
//
//        // Save updated list back to the file
//        try {
//            jfs.saveToFile(newRecyclingTips, FILE_PATH); // Overwrites the file with updated list
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete RecyclingTip from JSON file", e);
//        }
//    }
//
//
//    /**
//     * Initialize the JSON file with sample data if it doesn't exist.
//     */
//    @PostConstruct
//    private void init() throws IOException {
//        File file = new File(FILE_PATH);
//        if (!file.exists()) {
//            System.out.println("File " + FILE_PATH + " does not exist");
//            populate(jfs,file.getPath());}
//        if (file.exists()){
//            List<RecyclingTip> recyclingTips = getAllRecyclingTips();
//            // Return an empty list if the file content is empty or null
//            if (recyclingTips == null || recyclingTips.isEmpty()) {
//                System.out.println("Content Empty");
//                populate(jfs,file.getPath());
//            }
//
//
//        }
//
//
//    }
//    private void populate(JSONFileServices jfs, String filePath){
//        List<RecyclingTip> sampleData = List.of(
//                new RecyclingTip(5, "Metal", "Rinse and clean metal cans to prevent contamination in the recycling process."),
//                new RecyclingTip(6, "Paper", "Flatten cardboard boxes to save space in recycling bins."),
//                new RecyclingTip(7, "Hazardous", "Dispose of hazardous materials like batteries and paint at authorized hazardous waste facilities."),
//                new RecyclingTip(8, "General", "Avoid placing plastic bags in recycling bins, as they can tangle sorting machinery."),
//                new RecyclingTip(9, "General", "Check your local recycling rules to ensure you are disposing of waste correctly.")
//
//                );
//        try {
//            jfs.saveToFile(sampleData,filePath);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to initialize JSON file", e);
//        }
//    }
}



