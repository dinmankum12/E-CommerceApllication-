package com.dinesh.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinesh.entity.CustomerOrder;

public interface CustomerOrderRepo extends JpaRepository<CustomerOrder, Long>{
	Optional<CustomerOrder> findByCustomerIdAndItemSNo(String customerId, Long itemSNo);

	List<CustomerOrder> findByCustomerId(String customerId);

}