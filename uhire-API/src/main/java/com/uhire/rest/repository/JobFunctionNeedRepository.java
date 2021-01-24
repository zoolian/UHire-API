package com.uhire.rest.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.JobFunctionNeed;

@Repository
public interface JobFunctionNeedRepository extends MongoRepository<JobFunctionNeed, String>, QuerydslPredicateExecutor<JobFunctionNeed> {
    
	Optional<JobFunctionNeed> getByUsername(String username);
	JobFunctionNeed findByUsername(String username);

	Optional<JobFunctionNeed> getById(String id);

}
