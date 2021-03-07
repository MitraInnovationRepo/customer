package com.mitralabs.customer.repository;

import org.springframework.data.repository.CrudRepository;

import com.mitralabs.customer.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}
