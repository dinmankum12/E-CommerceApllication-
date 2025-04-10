package com.dinesh.controller;

import com.dinesh.dto.OrderItemDTO;
import com.dinesh.entity.Customer;
import com.dinesh.entity.CustomerOrder;
import com.dinesh.entity.DeliveredOrder;
import com.dinesh.entity.ItemName;
import com.dinesh.repo.CustomerOrderRepo;
import com.dinesh.repo.CustomerRepo;
import com.dinesh.repo.DeliveredOrderRepo;
import com.dinesh.repo.ItemNameRepo;
import com.dinesh.service.DeliveredOrderIdGeneratorService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class OrderController {

    @Autowired
    private ItemNameRepo itemNameRepo;
    
    @Autowired
    private CustomerRepo customerRepo;
    
    @Autowired
    private DeliveredOrderRepo deliveredOrderRepo;
    
    @Autowired
    private DeliveredOrderIdGeneratorService deliveredOrderIdGenerator;

    @Autowired
    private CustomerOrderRepo customerOrderRepo;

//    @PostMapping("/placeOrder")
//    public ResponseEntity<Object> placeOrder(@RequestBody List<OrderItemDTO> items, HttpSession session) {
//        String customerId = (String) session.getAttribute("customerId");
//
//        if (customerId == null) {
//            return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
//        }
//
//        List<CustomerOrder> orderListToSave = new ArrayList<>();
//
//        for (OrderItemDTO itemDTO : items) {
//            Optional<ItemName> itemOpt = itemNameRepo.findById(itemDTO.getItemSNo());
//
//            if (itemOpt.isEmpty()) {
//                return new ResponseEntity<>("Item not found: " + itemDTO.getItemSNo(), HttpStatus.NOT_FOUND);
//            }
//
//            ItemName item = itemOpt.get();
//
//            if (item.getNumberOfItemAvailable() < itemDTO.getQuantity()) {
//                return new ResponseEntity<>("Insufficient stock for item: " + item.getItemName(), HttpStatus.BAD_REQUEST);
//            }
//
//            int remaining = item.getNumberOfItemAvailable() - itemDTO.getQuantity();
//            item.setNumberOfItemAvailable(remaining);
//            item.setCalculateAllItemPriceAvailable(remaining * item.getPerItemPrice());
//            itemNameRepo.save(item);
//
//            // Check if this customer already ordered this item
//            Optional<CustomerOrder> existingOrderOpt = customerOrderRepo.findByCustomerIdAndItemSNo(customerId, item.getItemSNo());
//
//            if (existingOrderOpt.isPresent()) {
//                // Update existing order
//                CustomerOrder existingOrder = existingOrderOpt.get();
//                int newQuantity = existingOrder.getQuantityOrdered() + itemDTO.getQuantity();
//                existingOrder.setQuantityOrdered(newQuantity);
//                existingOrder.setTotalPrice(newQuantity * item.getPerItemPrice());
//
//                orderListToSave.add(existingOrder);
//            } else {
//                // New order
//                CustomerOrder newOrder = new CustomerOrder();
//                newOrder.setCustomerId(customerId);
//                newOrder.setItemSNo(item.getItemSNo());
//                newOrder.setItemName(item.getItemName());
//                newOrder.setQuantityOrdered(itemDTO.getQuantity());
//                newOrder.setPricePerItem(item.getPerItemPrice());
//                newOrder.setTotalPrice(itemDTO.getQuantity() * item.getPerItemPrice());
//
//                orderListToSave.add(newOrder);
//            }
//        }
//
//        customerOrderRepo.saveAll(orderListToSave);
//        return new ResponseEntity<>("Order placed successfully", HttpStatus.OK);
//    }
//    
//    @PostMapping("/deliverOrder")
//    public ResponseEntity<Object> deliverOrder(HttpSession session) {
//        String customerId = (String) session.getAttribute("customerId");
//
//        if (customerId == null) {
//            return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
//        }
//
//        return processDelivery(customerId);
//    }

    @PostMapping("/placeOrderByCustomerId/{customerId}")
    public ResponseEntity<Object> placeOrderByCustomerId(
            @PathVariable String customerId,
            @RequestBody List<OrderItemDTO> items) {
        
        Optional<Customer> customerOpt = customerRepo.findById(customerId);
        if (customerOpt.isEmpty()) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
        
        List<CustomerOrder> orderListToSave = new ArrayList<>();

        for (OrderItemDTO itemDTO : items) {
            Optional<ItemName> itemOpt = itemNameRepo.findById(itemDTO.getItemSNo());

            if (itemOpt.isEmpty()) {
                return new ResponseEntity<>("Item not found: " + itemDTO.getItemSNo(), HttpStatus.NOT_FOUND);
            }

            ItemName item = itemOpt.get();

            if (item.getNumberOfItemAvailable() < itemDTO.getQuantity()) {
                return new ResponseEntity<>("Insufficient stock for item: " + item.getItemName(), HttpStatus.BAD_REQUEST);
            }

            int remaining = item.getNumberOfItemAvailable() - itemDTO.getQuantity();
            item.setNumberOfItemAvailable(remaining);
            item.setCalculateAllItemPriceAvailable(remaining * item.getPerItemPrice());
            itemNameRepo.save(item);

            // Check if this customer already ordered this item
            Optional<CustomerOrder> existingOrderOpt = customerOrderRepo.findByCustomerIdAndItemSNo(customerId, item.getItemSNo());

            if (existingOrderOpt.isPresent()) {
                // Update existing order
                CustomerOrder existingOrder = existingOrderOpt.get();
                int newQuantity = existingOrder.getQuantityOrdered() + itemDTO.getQuantity();
                existingOrder.setQuantityOrdered(newQuantity);
                existingOrder.setTotalPrice(newQuantity * item.getPerItemPrice());

                orderListToSave.add(existingOrder);
            } else {
                // New order
                CustomerOrder newOrder = new CustomerOrder();
                newOrder.setCustomerId(customerId);
                newOrder.setItemSNo(item.getItemSNo());
                newOrder.setItemName(item.getItemName());
                newOrder.setQuantityOrdered(itemDTO.getQuantity());
                newOrder.setPricePerItem(item.getPerItemPrice());
                newOrder.setTotalPrice(itemDTO.getQuantity() * item.getPerItemPrice());

                orderListToSave.add(newOrder);
            }
        }

        customerOrderRepo.saveAll(orderListToSave);
        return new ResponseEntity<>("Order placed successfully for customer ID: " + customerId, HttpStatus.OK);
    }
    
    @PostMapping("/deliverOrderByCustomerId/{customerId}")
    public ResponseEntity<Object> deliverOrderByCustomerId(@PathVariable String customerId) {
        return processDelivery(customerId);
    }
    
    // Common method to process deliveries
    private ResponseEntity<Object> processDelivery(String customerId) {
        List<CustomerOrder> orders = customerOrderRepo.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            return new ResponseEntity<>("No orders found for customer", HttpStatus.NOT_FOUND);
        }

        Optional<Customer> customerOpt = customerRepo.findById(customerId);
        if (customerOpt.isEmpty()) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }

        Customer customer = customerOpt.get();
        List<DeliveredOrder> deliveredOrders = new ArrayList<>();
        int totalAmount = orders.stream().mapToInt(CustomerOrder::getTotalPrice).sum();
        
        // Generate a single deliveredOrderId for the entire delivery
        String deliveredOrderId = deliveredOrderIdGenerator.generateDeliveredOrderId();
        Date deliveryDate = new Date();

        for (CustomerOrder order : orders) {
            DeliveredOrder dOrder = new DeliveredOrder();
            // Use the same ID for all items in this delivery
            dOrder.setDeliveredOrderId(deliveredOrderId);
            dOrder.setCustomerId(customer.getCustomerId());
            dOrder.setCustomerName(customer.getCustomerName());
            dOrder.setDeliveryAddress(customer.getAddress());
            dOrder.setItemSNo(order.getItemSNo());
            dOrder.setItemName(order.getItemName());
            dOrder.setQuantityOrdered(order.getQuantityOrdered());
            dOrder.setPricePerItem(order.getPricePerItem());
            dOrder.setTotalPrice(order.getTotalPrice());
            dOrder.setTotalAmountToPay(totalAmount);
            // Use the same date for all items in this delivery
            dOrder.setDeliveryDate(deliveryDate);

            deliveredOrders.add(dOrder);
        }

        deliveredOrderRepo.saveAll(deliveredOrders);
        customerOrderRepo.deleteAll(orders);
        return new ResponseEntity<>("Order delivered successfully for customer ID: " + customerId, HttpStatus.OK);
    }
}