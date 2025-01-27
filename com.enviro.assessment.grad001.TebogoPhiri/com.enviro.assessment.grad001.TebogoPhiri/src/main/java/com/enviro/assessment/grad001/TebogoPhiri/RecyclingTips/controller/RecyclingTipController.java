package com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.controller;

import com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.model.RecyclingTip;
import com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.service.RecyclingTipRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waste/recycling-tips")
public class RecyclingTipController {

    private final RecyclingTipRepository recyclingRepository;
    public RecyclingTipController(RecyclingTipRepository recyclingRepository) {
        this.recyclingRepository = recyclingRepository;
    }

//    @GetMapping("")
//    List<RecyclingTip> getAllRecyclingTips(){
//        return recyclingRepository.getAllRecyclingTips();
//    }
//
//    @GetMapping("/{id}")
//    RecyclingTip findById(@PathVariable Integer id){
//        return recyclingRepository.findById(id);
//    }
//
//    //post
//    @ResponseStatus(HttpStatus.CREATED) //Gives a status of 201 instead of 200
//    @PostMapping("")
//    void createRecyclingTip(@Valid @RequestBody RecyclingTip recycleTip){recyclingRepository.create(recycleTip);}
//
//    //update AKA put
//    @ResponseStatus(HttpStatus.NO_CONTENT) //Gives a status of 204 instead of 200
//    @PutMapping("/{id}")
//    void updateRecyclingTip(@Valid @RequestBody RecyclingTip recycleTip, @PathVariable Integer id){recyclingRepository.update(recycleTip, id);}
//
//    //delete
//    @ResponseStatus(HttpStatus.NO_CONTENT) //Gives a status of 204 instead of 200
//    @DeleteMapping("/{id}")
//    void deleteRecyclingTip(@PathVariable Integer id){recyclingRepository.delete(id);}

}
