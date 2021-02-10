package com.uhire.rest.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String>, QuerydslPredicateExecutor<Employee> {

	@Query("{'status': { $exists: true } }")
	List<Employee> findAll();

	List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

	@Query(fields="{'id' : 1, 'firstName' : 1, 'lastName' : 1, 'email' : 1 }")
	List<Employee> findByFirstNameLikeAndLastNameLike(String firstName, String lastName);
	
	@Query(fields="{'id' : 1, 'firstName' : 1, 'lastName' : 1, 'email' : 1 }")
	List<Employee> findByFirstNameLike(String firstName);
	
	@Query(fields="{'id' : 1, 'firstName' : 1, 'lastName' : 1, 'email' : 1 }")
	List<Employee> findByLastNameLike(String lastName);
}
