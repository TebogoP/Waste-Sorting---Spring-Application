package com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.service;

import com.enviro.assessment.grad001.TebogoPhiri.WasteCategories.model.WasteCategory;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteCatRepository  extends ListCrudRepository<WasteCategory, Integer> {

}
