package com.uhire.rest.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>, QuerydslPredicateExecutor<User> {
    
	Optional<User> getByUsername(String username);
	User findByUsername(String username);

	Optional<User> getById(long id);

    void save(User user);
}
