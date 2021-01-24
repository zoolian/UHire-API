package com.uhire.rest.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String>, QuerydslPredicateExecutor<Employee> {
    
	Optional<Employee> getByUsername(String username);
	Employee findByUsername(String username);

	Optional<Employee> getById(String id);

}
