package com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.service;

import com.enviro.assessment.grad001.TebogoPhiri.RecyclingTips.model.RecyclingTip;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
/**
 * @Repository annotation marks this class as a Data Access Object (DAO).
 * This version uses a JSON file as the data storage for RecyclingTip objects
 */
@Repository
public interface RecycleRepository extends ListCrudRepository<RecyclingTip, Integer> {

}



