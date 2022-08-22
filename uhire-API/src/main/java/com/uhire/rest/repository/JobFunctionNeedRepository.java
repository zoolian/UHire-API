package com.uhire.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.JobFunctionNeed;

@Repository
public interface JobFunctionNeedRepository extends JpaRepository<JobFunctionNeed, Long>, QuerydslPredicateExecutor<JobFunctionNeed> {

}
