package com.example.myappDB.controller_test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.myappDB.controller.CustomerController;
import com.example.myappDB.entities.Customer;
import com.example.myappDB.exception.ApiRequestException;
import com.example.myappDB.services.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerControllerTest {
	
	@InjectMocks
	private CustomerController customerController;
	
	@Mock
	private CustomerService customerService;
	
	@Mock
	private static Customer customer;
	
	public static final Integer id = 100;
	public static final String string_id = String.valueOf(id);
	public static final String branch = "Pune";
	
	@BeforeClass
	public static void setUp() {
		customer = new Customer(id, "anshu", "gaur", "anshu.gaur@db.com", "Pune");
	}
	
	@AfterClass
	public static void tearDown() {
		customer = null;
	}
	
	@Test
	public void findCustomerByIdTest() {
		when(customerService.getCustomerById(id)).thenReturn(customer);
		assertEquals(customer, customerController.findCustomerById(string_id).getBody());
	}

	@Test(expected = ApiRequestException.class)
	public void parseIdTestWhenIdIsNotNumeric() {
		when(customerController.parseId("dummy_id")).thenThrow(ApiRequestException.class);
		customerController.parseId("dummy_id");
	}
	
	@Test(expected = ApiRequestException.class)
	public void findCustomerByIdTestWhenIdIsNotPresent() {
		when(customerService.getCustomerById(id)).thenReturn(null).thenThrow(ApiRequestException.class);
		customerController.findCustomerById(string_id);
	}
	
	@Test
	public void findAllCustomersTest() {
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
		when(customerService.getAllCustomers()).thenReturn(customers);
		assertEquals(customers.size(), customerController.findAllCustomers().getBody().size());
	}
	
	@Test
	public void findCustomerByBranchTest() {
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
		when(customerService.getCustomerByBranch(branch)).thenReturn(customers);
		assertEquals(customers.size(), customerController.findCustomerByBranch(branch).getBody().size());
	}
	
	@Test
	public void addNewCustomerTest() {
		assertEquals(HttpStatus.CREATED, customerController.addNewCustomer(customer).getStatusCode());
	}
	
	@Test
	public void updateCustomerTestWhenCustomerIsNotPresent() {
		when(customerService.getCustomerById(id)).thenReturn(null);
		assertEquals(HttpStatus.NOT_FOUND, customerController.updateCustomer(string_id, customer).getStatusCode());
	}
	
	@Test
	public void updateCustomerTestWhenCustomerIsPresent() {
		when(customerService.getCustomerById(id)).thenReturn(customer);
		assertEquals(HttpStatus.OK, customerController.updateCustomer(string_id, customer).getStatusCode());
	}
	
	@Test
	public void deleteCustomerTestWhenCustomerIsNotPresent() {
		when(customerService.getCustomerById(id)).thenReturn(null);
		assertEquals(HttpStatus.NOT_FOUND, customerController.deleteCustomer(string_id).getStatusCode());
	}
	
	@Test
	public void deleteCustomerTestWhenCustomerIsPresent() {
		when(customerService.getCustomerById(id)).thenReturn(customer);
		assertEquals(HttpStatus.OK, customerController.deleteCustomer(string_id).getStatusCode());
	}
}
