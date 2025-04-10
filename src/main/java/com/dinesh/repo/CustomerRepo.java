package com.dinesh.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinesh.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, String>{
	
	Optional<Customer> findByUserIdAndPassword(String userId, String password);
	
}
