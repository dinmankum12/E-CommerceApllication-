package com.dinesh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dinesh.repo.DeliveredOrderRepo;
import com.dinesh.entity.DeliveredOrder;

@Service
public class DeliveredOrderIdGeneratorService {

    @Autowired
    private DeliveredOrderRepo deliveredOrderRepo;

    public String generateDeliveredOrderId() {
        long count = deliveredOrderRepo.count() + 1;
        String id = String.format("DL%04d", count); // Start with 4 digits: DL0001, DL0002, ...
        if (id.length() > 11) { // DL + 9 digits max
            throw new RuntimeException("DeliveredOrder ID exceeded max allowed length");
        }
        return id;
    }
}
