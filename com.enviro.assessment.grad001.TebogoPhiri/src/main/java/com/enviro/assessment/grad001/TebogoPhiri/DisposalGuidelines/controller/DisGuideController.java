package com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.controller;

import com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.model.DisposalGuideline;
import com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.service.DisGuideRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waste/disposal-guideline")
public class DisGuideController {

    private final DisGuideRepository guidelineRepository;
    public DisGuideController(DisGuideRepository guidelineRepository) {
        this.guidelineRepository = guidelineRepository;
    }

//    @GetMapping("")
//    List<DisposalGuideline> getAllDisposalGuidelines(){
//        return guidelineRepository.getAllDisposalGuideline();
//    }
//
//    @GetMapping("/{id}")
//    DisposalGuideline findById(@PathVariable Integer id){
//        return guidelineRepository.findById(id);
//    }
//
//    //post
//    @ResponseStatus(HttpStatus.CREATED) //Gives a status of 201 instead of 200
//    @PostMapping("")
//    void createDisposalGuideline(@Valid @RequestBody DisposalGuideline disposalGuideline){guidelineRepository.create(disposalGuideline);}
//
//    //update AKA put
//    @ResponseStatus(HttpStatus.NO_CONTENT) //Gives a status of 204 instead of 200
//    @PutMapping("/{id}")
//    void updateDisposalGuideline(@Valid @RequestBody DisposalGuideline disposalGuideline, @PathVariable Integer id){guidelineRepository.update(disposalGuideline, id);}
//
//    //delete
//    @ResponseStatus(HttpStatus.NO_CONTENT) //Gives a status of 204 instead of 200
//    @DeleteMapping("/{id}")
//    void deleteDisposalGuideline(@PathVariable Integer id){guidelineRepository.delete(id);}

}
