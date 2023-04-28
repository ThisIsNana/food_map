package com.example.food_map.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.food_map.entity.Store;

@Repository
public interface StoreDAO extends JpaRepository<Store, String>{
	
}
