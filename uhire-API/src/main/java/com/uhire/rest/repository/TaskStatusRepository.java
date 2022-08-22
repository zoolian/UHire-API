package com.uhire.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.lists.TaskStatus;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long>, QuerydslPredicateExecutor<TaskStatus> {

	TaskStatus findByName(String string);
}
