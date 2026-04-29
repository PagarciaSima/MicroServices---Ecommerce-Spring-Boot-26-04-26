package com.pgs.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pgs.ecommerce.entity.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String > {

}