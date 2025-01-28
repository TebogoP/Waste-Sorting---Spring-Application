package com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.controller;

import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.model.WasteCategory;
import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.service.JdbcClientRepos;
import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.service.WasteCatRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/waste/category")
public class WasteCatController {

    private final WasteCatRepository wasteCatRepository;
    private final JdbcClientRepos JdbcRepository;


    public WasteCatController(WasteCatRepository wasteCatRepository, JdbcClientRepos jdbcRepository) {
        this.wasteCatRepository = wasteCatRepository;
        JdbcRepository = jdbcRepository;
    }

    // Get all categories
    @GetMapping("")
    public List<WasteCategory> findAll() {
        return wasteCatRepository.findAll();
    }

    // Get category by ID
    @GetMapping("/{id}")
    public WasteCategory findById(@PathVariable Integer id) {
        return wasteCatRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WasteCategory not found with ID " + id));
    }

    // Create a new category
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void createCategory(@Valid @RequestBody WasteCategory wasteCat) {
        if (wasteCatRepository.existsById(wasteCat.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "WasteCategory with ID " + wasteCat.getId() + " already exists");
        }
        wasteCatRepository.save(wasteCat);
    }

    // Update an existing category
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateCategory(@Valid @RequestBody WasteCategory wasteCat, @PathVariable Integer id) {
        if (!wasteCatRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WasteCategory not found with ID " + id);
        }
         JdbcRepository.update(wasteCat,id);
    }

    // Delete a category
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        if (!wasteCatRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WasteCategory not found with ID " + id);
        }
        wasteCatRepository.deleteById(id);
    }
}
