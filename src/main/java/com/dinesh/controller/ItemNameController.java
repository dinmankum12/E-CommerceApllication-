package com.dinesh.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.entity.ItemName;
import com.dinesh.repo.ItemNameRepo;

@RestController
public class ItemNameController {
	@Autowired
	private ItemNameRepo itemNameRepo;
	
	@PostMapping("/itemAdded")
	public ResponseEntity<Object> addItem(@RequestBody List<ItemName> items) {
		
		 for (ItemName item : items) {
		        int total = item.getPerItemPrice() * item.getNumberOfItemAvailable();
		        item.setCalculateAllItemPriceAvailable(total);
		    }
		 
		itemNameRepo.saveAll(items);
		return new ResponseEntity<>("Item(s) saved/updated successfully", HttpStatus.OK);
		
	}
	
	@PostMapping("/updateItemNameById/{id}")
	public ResponseEntity<Object> updateItemNameById(@PathVariable("id") Long itemSNo, @RequestBody ItemName newItemData) {
	    Optional<ItemName> oldItemData = itemNameRepo.findById(itemSNo);
	    
	    if (oldItemData.isPresent()) {
	        ItemName updateItemData = oldItemData.get();
	        
	        updateItemData.setItemName(newItemData.getItemName());
	        updateItemData.setPerItemPrice(newItemData.getPerItemPrice());
	        updateItemData.setNumberOfItemAvailable(newItemData.getNumberOfItemAvailable());

	        // Recalculate total price
	        int total = newItemData.getPerItemPrice() * newItemData.getNumberOfItemAvailable();
	        updateItemData.setCalculateAllItemPriceAvailable(total);
	        
	        ItemName updatedItem = itemNameRepo.save(updateItemData);
	        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
	    }

	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/decreaseItemCount/{itemSNo}/{decreaseBy}")
	public ResponseEntity<Object> decreaseItemCount(@PathVariable Long itemSNo, @PathVariable int decreaseBy) {
	    ItemName item = itemNameRepo.findById(itemSNo).orElse(null);
	    
	    if (item == null) {
	        return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
	    }

	    int updatedCount = item.getNumberOfItemAvailable() - decreaseBy;
	    if (updatedCount < 0) {
	        return new ResponseEntity<>("Item count cannot be negative", HttpStatus.BAD_REQUEST);
	    }

	    item.setNumberOfItemAvailable(updatedCount);
	    item.setCalculateAllItemPriceAvailable(updatedCount * item.getPerItemPrice());

	    itemNameRepo.save(item);
	    return new ResponseEntity<>("Item count updated successfully", HttpStatus.OK);
	}
	
	
	

}