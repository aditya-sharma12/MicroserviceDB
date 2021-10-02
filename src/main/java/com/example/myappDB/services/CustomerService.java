package com.example.myappDB.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myappDB.dao.CustomerRepository;
import com.example.myappDB.entities.Customer;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	
	public void addCustomer(Customer customer) {
		customerRepository.save(customer);
	}
	
	public Customer getCustomerById(Integer id) {
		if(!customerRepository.findById(id).isPresent()) 
			return null;
		return customerRepository.findById(id).get();
	}

	public List<Customer> getAllCustomers() {
		return (List<Customer>)customerRepository.findAll();
	}
	
	public List<Customer> getCustomerByBranch(String branch) {
		return (List<Customer>)customerRepository.findByBranch(branch);
	}
	
	public void delete(Integer id) {
		customerRepository.deleteById(id);
	}
	
	public void update(Integer id, Customer updatedCustomer) {
		Customer customerToBeUpdated = customerRepository.findById(id).get();
		customerToBeUpdated.setFirstName(updatedCustomer.getFirstName());
		customerToBeUpdated.setLastName(updatedCustomer.getLastName());
		customerToBeUpdated.setEmail(updatedCustomer.getEmail());
		customerToBeUpdated.setBranch(updatedCustomer.getBranch());
		customerRepository.save(customerToBeUpdated);		
	}
}
