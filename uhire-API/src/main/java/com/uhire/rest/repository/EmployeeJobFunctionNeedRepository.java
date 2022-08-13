package com.uhire.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.EmployeeJobFunctionNeed;

@Repository
public interface EmployeeJobFunctionNeedRepository extends JpaRepository<EmployeeJobFunctionNeed, Long>, QuerydslPredicateExecutor<EmployeeJobFunctionNeed> {

	List<EmployeeJobFunctionNeed> findByEmployeeId(long id);

	Optional<EmployeeJobFunctionNeed> findByNeedIdAndEmployeeId(long needId, long employeeId);

	List<EmployeeJobFunctionNeed> findByNeedId(long id);

}
