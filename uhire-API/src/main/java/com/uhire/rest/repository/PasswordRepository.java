package com.uhire.rest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.Password;

@Repository
public interface PasswordRepository extends MongoRepository<Password, String>, QuerydslPredicateExecutor<Password>  {
	Password findByPersonId(String id);
}
