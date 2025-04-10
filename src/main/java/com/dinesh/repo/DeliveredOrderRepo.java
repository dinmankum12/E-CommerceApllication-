package com.dinesh.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dinesh.entity.DeliveredOrder;

public interface DeliveredOrderRepo extends JpaRepository<DeliveredOrder, String> {
}
