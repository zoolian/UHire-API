package com.uhire.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.uhire.rest.model.lists.PayType;

@Repository
public interface PayTypeRepository extends JpaRepository<PayType, Integer>, QuerydslPredicateExecutor<PayType> {

}
