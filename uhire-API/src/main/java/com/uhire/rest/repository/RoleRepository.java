package com.uhire.rest.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, String>, QuerydslPredicateExecutor<Role>  {

	Role findByName(String string);
	Optional<Role> findById(String id);
}
