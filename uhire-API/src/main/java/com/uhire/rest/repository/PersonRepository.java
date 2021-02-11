package com.uhire.rest.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person, String>, QuerydslPredicateExecutor<Person> {
	Person findByEmail(String email);
	
	@Query(fields="{'id' : 1, 'firstName' : 1, 'lastName' : 1, 'email' : 1 }")
	List<Person> findByFirstNameLikeAndLastNameLikeIgnoreCase(String firstName, String lastName);
	
	@Query(fields="{'id' : 1, 'firstName' : 1, 'lastName' : 1, 'email' : 1 }")
	List<Person> findByFirstNameLikeIgnoreCase(String firstName);
	
	@Query(fields="{'id' : 1, 'firstName' : 1, 'lastName' : 1, 'email' : 1 }")
	List<Person> findByLastNameLikeIgnoreCase(String lastName);
}
