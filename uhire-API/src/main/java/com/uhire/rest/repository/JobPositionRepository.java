package com.uhire.rest.repository;

import org.springframework.data.mongodb.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.JobPosition;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, String>, QuerydslPredicateExecutor<JobPosition> {

	JobPosition getById(long id);

}
