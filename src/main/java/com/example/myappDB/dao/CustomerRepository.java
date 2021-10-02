package com.example.myappDB.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.myappDB.entities.Customer;

//To create a connection with a database for the Customer entity
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	
	List<Customer> findByBranch(String branch);
}