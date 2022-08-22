package com.uhire.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.JobPosition;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long>, QuerydslPredicateExecutor<JobPosition> {

	JobPosition getById(long id);

}
