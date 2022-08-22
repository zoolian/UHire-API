package com.uhire.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, QuerydslPredicateExecutor<Person> {
	Person findByEmail(String email);
	
	List<Person> findByFirstNameLikeAndLastNameLikeIgnoreCase(String firstName, String lastName);
	
	List<Person> findByFirstNameLikeIgnoreCase(String firstName);
	
	List<Person> findByLastNameLikeIgnoreCase(String lastName);

    Optional<Object> findById(long id);
}
