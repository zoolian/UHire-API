package com.uhire.rest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.lists.WorkFrequency;

@Repository
public interface WorkFrequencyRepository extends MongoRepository<WorkFrequency, Integer>, QuerydslPredicateExecutor<WorkFrequency> {

}
