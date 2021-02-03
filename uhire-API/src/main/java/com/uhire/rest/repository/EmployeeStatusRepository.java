package com.uhire.rest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.lists.EmployeeStatus;

@Repository
public interface EmployeeStatusRepository extends MongoRepository<EmployeeStatus, Integer>, QuerydslPredicateExecutor<EmployeeStatus> {

}
