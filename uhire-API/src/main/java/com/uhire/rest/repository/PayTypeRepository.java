package com.uhire.rest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.lists.PayType;

@Repository
public interface PayTypeRepository extends MongoRepository<PayType, Integer>, QuerydslPredicateExecutor<PayType> {

}
