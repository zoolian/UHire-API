package com.uhire.rest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.JobFunctionNeed;

@Repository
public interface JobFunctionNeedRepository extends MongoRepository<JobFunctionNeed, String>, QuerydslPredicateExecutor<JobFunctionNeed> {

}
