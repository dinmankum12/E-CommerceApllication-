package com.dinesh.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "delivered_orders")
public class DeliveredOrder {

    @Id
    private String deliveredOrderId;

    private String customerId;
    private String customerName;
    private String deliveryAddress;

    private Long itemSNo;
    private String itemName;
    private int quantityOrdered;
    private int pricePerItem;
    private int totalPrice;

    private int totalAmountToPay;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

	public String getDeliveredOrderId() {
		return deliveredOrderId;
	}

	public void setDeliveredOrderId(String deliveredOrderId) {
		this.deliveredOrderId = deliveredOrderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Long getItemSNo() {
		return itemSNo;
	}

	public void setItemSNo(Long itemSNo) {
		this.itemSNo = itemSNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantityOrdered() {
		return quantityOrdered;
	}

	public void setQuantityOrdered(int quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}

	public int getPricePerItem() {
		return pricePerItem;
	}

	public void setPricePerItem(int pricePerItem) {
		this.pricePerItem = pricePerItem;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTotalAmountToPay() {
		return totalAmountToPay;
	}

	public void setTotalAmountToPay(int totalAmountToPay) {
		this.totalAmountToPay = totalAmountToPay;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

    // Getters and Setters
}
