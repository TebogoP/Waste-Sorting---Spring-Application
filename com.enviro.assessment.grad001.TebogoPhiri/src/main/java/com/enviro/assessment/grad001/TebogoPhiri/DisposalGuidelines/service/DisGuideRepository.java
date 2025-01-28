package com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.service;

import com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.model.DisposalGuideline;
import com.enviro.assessment.grad001.TebogoPhiri.Generic.JSONFileServices;
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
 * This version uses a JSON file as the data storage for DisposalGuideline objects
 */
@Repository
public class DisGuideRepository {

    private static final String FILE_PATH = "waste_guidelines.json";
    private static final Logger log = LoggerFactory.getLogger(DisGuideRepository.class);
//    JSONFileServices jfs = new JSONFileServices();
//
//    /**
//     * Retrieve all DisposalGuideline objects from the JSON file.
//     * @return List of all DisposalGuideline objects.
//     */
//    public List<DisposalGuideline> getAllDisposalGuideline() {
//        try {
//            List<DisposalGuideline> guidelines = jfs.readFromFile(FILE_PATH, DisposalGuideline.class);
//
//            if (guidelines == null || guidelines.isEmpty()) {
//                return new ArrayList<>(); // Return an empty list if no data is present
//            }
//            return guidelines;
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read from JSON file", e);
//        }
//    }
//
//
//    /**
//     * Find a DisposalGuideline by its ID.
//     *
//     * @param id The ID of the DisposalGuideline to find.
//     * @return The DisposalGuideline object if found.
//     * @throws ResponseStatusException if the guideline is not found.
//     */
//    public DisposalGuideline findById(Integer id) {
//        List<DisposalGuideline> guidelines = getAllDisposalGuideline();
//        for (DisposalGuideline guideline : guidelines) {
//            if (guideline.getId().equals(id)) {
//                return guideline;
//            }
//        }
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DisposalGuideline with ID " + id + " not found");
//    }
//
//    /**
//     * Create a new DisposalGuideline and save it to the JSON file.
//     * @param newGuideline The DisposalGuideline to create.
//     */
//    public void create(DisposalGuideline newGuideline) {
//        if (newGuideline.getId() == null || newGuideline.getGuideline() == null || newGuideline.getWasteType() == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid DisposalGuideline: ID, Guideline, and Waste Type must not be null");
//        }
//        List<DisposalGuideline> guidelines = getAllDisposalGuideline();
//        DisposalGuideline guidelineFound = null;
//        try{
//            guidelineFound = findById(newGuideline.getId());
//        }
//        catch (ResponseStatusException e) {
//            guidelines.add(newGuideline);
//        }
//        if (guidelineFound != null) {
//            throw new ResponseStatusException(HttpStatus.FOUND, "DisposalGuideline with ID " + guidelineFound.getId() + " already exist");
//        }
//
//
//        try {
//            jfs.saveToFile(guidelines,FILE_PATH);
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save DisposalGuideline to JSON file", e);
//        }
//    }
//
//
//    /**
//     * Update an existing DisposalGuideline by its ID.
//     * @param updatedGuideline The updated DisposalGuideline data.
//     * @param id              The ID of the DisposalGuideline to update.
//     */
//    public void update(DisposalGuideline updatedGuideline, Integer id) {
//        List<DisposalGuideline> guidelines = getAllDisposalGuideline();
//        List<DisposalGuideline> newGuidelines = new ArrayList<>();
//        // Try to find and remove the guideline
//        try {
//            DisposalGuideline existingDisposalGuideline = findById(id);
//            for (DisposalGuideline guideline : guidelines) {
//                if (guideline.getId().equals(existingDisposalGuideline.getId())) {
//                    updatedGuideline.setId(id);
//                    newGuidelines.add(updatedGuideline);
//                    // Directly remove the found guideline
//                }else {
//                    newGuidelines.add(guideline);
//                }
//            }
//
//        }
//        catch (ResponseStatusException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DisposalGuideline with ID " + id + " not found");
//        }
//
//        try {
//            jfs.saveToFile(newGuidelines,FILE_PATH);
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update DisposalGuideline in JSON file", e);
//        }
//    }
//
//    /**
//     * Delete a DisposalGuideline by its ID.
//     * @param id The ID of the DisposalGuideline to delete.
//     */
//    public void delete(Integer id) {
//        List<DisposalGuideline> guidelines = getAllDisposalGuideline();
//        List<DisposalGuideline> newGuidelines = new ArrayList<>();
//
//        // Try to find and remove the guideline
//        try {
//            DisposalGuideline existingDisposalGuideline = findById(id);
//            for (DisposalGuideline guideline : guidelines) {
//                if (guideline.getId().equals(existingDisposalGuideline.getId())) {
//                    continue; // Directly remove the found guideline
//                }
//                newGuidelines.add(guideline);
//            }
//            //guidelines.remove(existingDisposalGuideline); /
//            System.out.println("DisposalGuideline with ID " + id + " has been removed.");
//        } catch (ResponseStatusException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DisposalGuideline with ID " + id + " not found", e);
//        }
//
//        // Save updated list back to the file
//        try {
//            jfs.saveToFile(newGuidelines, FILE_PATH); // Overwrites the file with updated list
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete DisposalGuideline from JSON file", e);
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
//            List<DisposalGuideline> guidelines = getAllDisposalGuideline();
//            // Return an empty list if the file content is empty or null
//            if (guidelines == null || guidelines.isEmpty()) {
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
//        List<DisposalGuideline> sampleData = List.of(
//                new DisposalGuideline(1, "Construction Waste", "Sort concrete, wood, and drywall for proper recycling or disposal."),
//                new DisposalGuideline(2, "Medical Waste", "Place sharps (like needles) in puncture-resistant containers before disposal."),
//                new DisposalGuideline(3, "Hazardous Waste", "Dispose of batteries and paint at authorized hazardous waste facilities.")
//        );
//        try {
//            jfs.saveToFile(sampleData,filePath);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to initialize JSON file", e);
//        }
//    }
}



