package com.dinesh.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="itemName")
@NoArgsConstructor
@AllArgsConstructor
public class ItemName {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long itemSNo;
	private String itemName;
	private int perItemPrice;
	private int numberOfItemAvailable;
	private int calculateAllItemPriceAvailable;
	
	public int getCalculateAllItemPriceAvailable() {
		return calculateAllItemPriceAvailable;
	}
	public void setCalculateAllItemPriceAvailable(int calculateAllItemPriceAvailable) {
		this.calculateAllItemPriceAvailable = calculateAllItemPriceAvailable;
	}
	public int getPerItemPrice() {
		return perItemPrice;
	}
	public void setPerItemPrice(int perItemPrice) {
		this.perItemPrice = perItemPrice;
	}
	public long getItemSNo() {
		return itemSNo;
	}
	public void setItemSNo(long itemSNo) {
		this.itemSNo = itemSNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getNumberOfItemAvailable() {
		return numberOfItemAvailable;
	}
	public void setNumberOfItemAvailable(int numberOfItemAvailable) {
		this.numberOfItemAvailable = numberOfItemAvailable;
	}
	
	

}
