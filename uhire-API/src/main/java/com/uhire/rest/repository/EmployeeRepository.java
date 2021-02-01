package com.uhire.rest.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String>, QuerydslPredicateExecutor<Employee> {

	@Query("{'status': 1}")
	List<Employee> findAll();
}
