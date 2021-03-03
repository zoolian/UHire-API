package com.uhire.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.EmployeeJobFunctionNeed;

@Repository
public interface EmployeeJobFunctionNeedRepository extends MongoRepository<EmployeeJobFunctionNeed, String>, QuerydslPredicateExecutor<EmployeeJobFunctionNeed> {

	List<EmployeeJobFunctionNeed> findByEmployeeId(String id);

	Optional<EmployeeJobFunctionNeed> findByNeedIdAndEmployeeId(String needId, String employeeId);

	List<EmployeeJobFunctionNeed> findByNeedId(String id);

}
