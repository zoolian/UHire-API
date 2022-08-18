package com.uhire.rest.repository;

import org.springframework.data.mongodb.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.Password;

@Repository
public interface PasswordRepository extends JpaRepository<Password, String>, QuerydslPredicateExecutor<Password>  {
	Password findByPersonId(long id);

	void save(Password password);
}
