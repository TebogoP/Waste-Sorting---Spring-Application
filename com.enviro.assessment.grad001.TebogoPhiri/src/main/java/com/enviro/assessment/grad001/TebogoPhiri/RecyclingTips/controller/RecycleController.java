package com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.controller;

import com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.model.RecyclingTip;
import com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.service.RecycleRepository;
import com.enviro.assessment.grad001.TebogoPhiri.Generic.Service.JdbcClientRepos;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/waste/recycle")
public class RecycleController {

        private final RecycleRepository recyclingRepository;
    private final JdbcClientRepos JdbcRepository;
    public RecycleController(RecycleRepository recyclingRepository, JdbcClientRepos jdbcRepository) {

        JdbcRepository = jdbcRepository;
        this.recyclingRepository = recyclingRepository;
    }

    @GetMapping("")
     public ResponseEntity<List<RecyclingTip>>findAll(){

        List<RecyclingTip> rcycleList = recyclingRepository.findAll();
        return ResponseEntity.ok(rcycleList); // Return 200 OK with recycletip list
    }


    @GetMapping("/{id}")
    public ResponseEntity<RecyclingTip>  findById(@PathVariable Integer id){
        RecyclingTip rcycle  =  recyclingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recycle tip not found with ID " + id));
        return ResponseEntity.status(HttpStatus.FOUND).body(rcycle); // Return 302 FOUND with guide
    }

    //post
    @ResponseStatus(HttpStatus.CREATED) //Gives a status of 201 instead of 200
    @PostMapping("")
    public ResponseEntity<String> createRecyclingTip(@Valid @RequestBody RecyclingTip recycleTip){
        if (recyclingRepository.existsById(recycleTip.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Recycle tip with ID " + recycleTip.getId() + " already exists");
        }
        recyclingRepository.save(recycleTip);
        return ResponseEntity.status(HttpStatus.CREATED).body("Recycle tip created successfully");
    }

    //update AKA put
//Gives a status of 204 instead of 200
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecyclingTip(@Valid @RequestBody RecyclingTip recycleTip, @PathVariable Integer id) {
        if (!recyclingRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recycle tip not found with ID " + id);
        }
        JdbcRepository.update(recycleTip,id, RecyclingTip.class);
        return ResponseEntity.status(HttpStatus.OK).body("Recycle tip updated successfully");
    }

    //delete
//Gives a status of 204 instead of 200
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecyclingTip(@PathVariable Integer id){
        if (!recyclingRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recycle tip not found with ID " + id);
        }
        recyclingRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Recycle tip deleted successfully");
    }


}
