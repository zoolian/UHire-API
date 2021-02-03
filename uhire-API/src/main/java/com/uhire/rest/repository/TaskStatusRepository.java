package com.uhire.rest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.lists.TaskStatus;

@Repository
public interface TaskStatusRepository extends MongoRepository<TaskStatus, Integer>, QuerydslPredicateExecutor<TaskStatus> {

	TaskStatus findByName(String string);
}
