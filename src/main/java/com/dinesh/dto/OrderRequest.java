package com.dinesh.dto;


import java.util.List;

public class OrderRequest {
    private String customerId;
    private List<OrderItemDTO> items;
    
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public List<OrderItemDTO> getItems() {
		return items;
	}
	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}
    
    
}


