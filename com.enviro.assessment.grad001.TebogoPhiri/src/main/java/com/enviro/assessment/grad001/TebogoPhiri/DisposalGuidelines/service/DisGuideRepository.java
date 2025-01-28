package com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.service;

import com.enviro.assessment.grad001.TebogoPhiri.DisposalGuidelines.model.DisposalGuideline;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Repository annotation marks this class as a Data Access Object (DAO).
 * This version uses a JSON file as the data storage for DisposalGuideline objects
 */
@Repository
public interface DisGuideRepository extends ListCrudRepository<DisposalGuideline, Integer> {

}



