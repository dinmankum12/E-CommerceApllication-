package com.dinesh.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinesh.entity.ItemName;

public interface ItemNameRepo extends JpaRepository<ItemName, Long>{

}
