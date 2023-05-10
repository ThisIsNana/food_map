package com.example.food_map.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.food_map.entity.Food;
import com.example.food_map.entity.FoodId;

@Repository
public interface FoodDAO extends JpaRepository<Food, FoodId>{
	
	public Food findByIdStoreNameAndIdFoodName(String storeName, String foodName);
	
	public List<Food> findAllByStoreName(String storeName);

}
