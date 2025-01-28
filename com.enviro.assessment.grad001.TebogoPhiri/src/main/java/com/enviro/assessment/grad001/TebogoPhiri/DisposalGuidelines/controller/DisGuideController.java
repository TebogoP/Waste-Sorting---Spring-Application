package com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.controller;

import com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.model.DisposalGuideline;
import com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.service.DisGuideRepository;
import com.enviro.assessment.grad001.TebogoPhiri.Generic.Service.JdbcClientRepos;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/waste/disposal")
public class DisGuideController {

    private final DisGuideRepository guidelineRepository;
    private final JdbcClientRepos JdbcRepository;
    public DisGuideController(DisGuideRepository guidelineRepository, JdbcClientRepos jdbcRepository) {

        JdbcRepository = jdbcRepository;
        this.guidelineRepository = guidelineRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<DisposalGuideline>> findAll() {
        List<DisposalGuideline> guides = guidelineRepository.findAll();
        return ResponseEntity.ok(guides); // Return 200 OK with guides list
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisposalGuideline> findById(@PathVariable Integer id) {
        DisposalGuideline guide = guidelineRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disposal guideline not found with ID " + id));
        return ResponseEntity.status(HttpStatus.FOUND).body(guide); // Return 302 FOUND with guide

    }

    //post
//Gives a status of 201 instead of 200
    @PostMapping("")
    public ResponseEntity<String>  createDisposalGuideline(@Valid @RequestBody DisposalGuideline disposalGuideline){
        if (guidelineRepository.existsById(disposalGuideline.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Disposal guideline with ID " + disposalGuideline.getId() + " already exists");
        }
        guidelineRepository.save(disposalGuideline);
        return ResponseEntity.status(HttpStatus.CREATED).body("Disposal guideline created successfully");
    }

    //update AKA put
//Gives a status of 204 instead of 200
    @PutMapping("/{id}")
    public ResponseEntity<String>  updateDisposalGuideline(@Valid @RequestBody DisposalGuideline disposalGuideline, @PathVariable Integer id) {
        if (!guidelineRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disposal guideline not found with ID " + id);
        }
        JdbcRepository.update(disposalGuideline,id, DisposalGuideline.class);
        return ResponseEntity.status(HttpStatus.OK).body("Disposal guideline updated successfully");
    }

    //delete
//Gives a status of 204 instead of 200
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDisposalGuideline(@PathVariable Integer id){
        if (!guidelineRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disposal guideline not found with ID " + id);
        }
        guidelineRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Disposal guideline deleted successfully");
    }

}
