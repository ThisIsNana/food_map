package com.example.food_map.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.food_map.entity.Food;
import com.example.food_map.entity.FoodId;

@Repository
public interface FoodDAO extends JpaRepository<Food, FoodId>{

}
