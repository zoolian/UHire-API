package com.uhire.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, QuerydslPredicateExecutor<Employee> {

	List<Employee> findAll();

	List<Employee> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

	List<Employee> findByFirstNameLikeAndLastNameLikeIgnoreCase(String firstName, String lastName);

	List<Employee> findByFirstNameLikeIgnoreCase(String firstName);

	List<Employee> findByLastNameLikeIgnoreCase(String lastName);

	Employee getById(long id);

}
