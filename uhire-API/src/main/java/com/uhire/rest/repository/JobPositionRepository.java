package com.uhire.rest.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.JobPosition;

@Repository
public interface JobPositionRepository extends MongoRepository<JobPosition, String>, QuerydslPredicateExecutor<JobPosition> {
    
	Optional<JobPosition> getByUsername(String username);
	JobPosition findByUsername(String username);

	Optional<JobPosition> getById(String id);

}
