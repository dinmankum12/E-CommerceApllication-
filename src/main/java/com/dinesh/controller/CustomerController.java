package com.dinesh.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.entity.Customer;
import com.dinesh.repo.CustomerRepo;

import jakarta.servlet.http.HttpSession;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@PostMapping("/registerCustomer")
	public ResponseEntity<Object> registerCustomer(@RequestBody Customer customer) {
	    long customerCount = customerRepo.count();
	    customer.setCustomerId("CU" + (100 + customerCount + 1));
	    customerRepo.save(customer);
	    return ResponseEntity.ok("Customer registered successfully with ID: " + customer.getCustomerId());
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Customer loginRequest, HttpSession session) {
	    Optional<Customer> customerOpt = customerRepo.findByUserIdAndPassword(
	            loginRequest.getUserId(), loginRequest.getPassword());

	    if (customerOpt.isPresent()) {
	        session.setAttribute("customerId", customerOpt.get().getCustomerId());
	        return ResponseEntity.ok("Login successful");
	    } else {
	        return new ResponseEntity<>("Invalid userId or password", HttpStatus.UNAUTHORIZED);
	    }
	}
}