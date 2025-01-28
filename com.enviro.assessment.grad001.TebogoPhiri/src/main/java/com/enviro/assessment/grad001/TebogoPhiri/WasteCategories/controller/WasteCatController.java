package com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.controller;

import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.model.WasteCategory;
import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.service.JdbcClientReposWC;
import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.service.WasteCatRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/api/waste/category")
public class WasteCatController {

    private final WasteCatRepository wasteCatRepository;
    private final JdbcClientReposWC JdbcRepository;


    public WasteCatController(WasteCatRepository wasteCatRepository, JdbcClientReposWC jdbcRepository) {
        this.wasteCatRepository = wasteCatRepository;
        JdbcRepository = jdbcRepository;
    }

    // Get all categories
    @GetMapping("")
    public ResponseEntity<List<WasteCategory>> findAll() {
        List<WasteCategory> categories = wasteCatRepository.findAll();
        return ResponseEntity.ok(categories); // Return 200 OK with categories list
    }

    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<WasteCategory> findById(@PathVariable Integer id) {
        WasteCategory category = wasteCatRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WasteCategory not found with ID " + id));
        return ResponseEntity.status(HttpStatus.FOUND).body(category); // Return 302 FOUND with category
    }

    // Create a new category
    @PostMapping("")
    public ResponseEntity<String> createCategory(@Valid @RequestBody WasteCategory wasteCat) {
        if (wasteCatRepository.existsById(wasteCat.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "WasteCategory with ID " + wasteCat.getId() + " already exists");
        }
        wasteCatRepository.save(wasteCat);
        return ResponseEntity.status(HttpStatus.CREATED).body("WasteCategory created successfully");
    }

    // Update an existing category

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody WasteCategory wasteCat, @PathVariable Integer id) {
        if (!wasteCatRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WasteCategory not found with ID " + id);
        }
        JdbcRepository.update(wasteCat, id);
        return ResponseEntity.status(HttpStatus.OK).body("WasteCategory updated successfully");
    }

    // Delete a category

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        if (!wasteCatRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WasteCategory not found with ID " + id);
        }
        wasteCatRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("WasteCategory deleted successfully");
    }
}
