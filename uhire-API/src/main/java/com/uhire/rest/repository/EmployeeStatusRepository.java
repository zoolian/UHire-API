package com.uhire.rest.repository;

import org.springframework.data.mongodb.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.lists.EmployeeStatus;

@Repository
public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, Integer>, QuerydslPredicateExecutor<EmployeeStatus> {

}
