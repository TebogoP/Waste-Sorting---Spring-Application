package com.enviro.assessment.grad001.TebogoPhiri.controller;

import com.enviro.assessment.grad001.TebogoPhiri.model.WasteCategory;
import com.enviro.assessment.grad001.TebogoPhiri.service.WasteCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waste-category")
public class WasteCategoryController {

    private final WasteCategoryRepository wasteCatRepository;
    public WasteCategoryController(WasteCategoryRepository wasteCatRepository) {
        this.wasteCatRepository = wasteCatRepository;
    }

    @GetMapping("")
    List<WasteCategory> getAllCategories(){
        return wasteCatRepository.getAllCategories();
    }

    @GetMapping("/{id}")
    WasteCategory findById(@PathVariable Integer id){
        return wasteCatRepository.findById(id);
    }

    //post
    @ResponseStatus(HttpStatus.CREATED) //Gives a status of 201 instead of 200
    @PostMapping("")
    void createCategory(@RequestBody WasteCategory wasteCat){wasteCatRepository.create(wasteCat);}

    //update AKA put
    @ResponseStatus(HttpStatus.NO_CONTENT) //Gives a status of 204 instead of 200
    @PutMapping("/{id}")
    void updateCategory(@RequestBody WasteCategory wasteCat, @PathVariable Integer id){wasteCatRepository.update(wasteCat, id);}

    //delete
    @ResponseStatus(HttpStatus.NO_CONTENT) //Gives a status of 204 instead of 200
    @DeleteMapping("/{id}")
    void deleteCategory(@PathVariable Integer id){wasteCatRepository.delete(id);}

}
