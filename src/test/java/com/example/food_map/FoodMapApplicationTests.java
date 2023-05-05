package com.example.food_map;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.food_map.entity.Food;
import com.example.food_map.entity.Store;
import com.example.food_map.service.ifs.FoodMapService;
import com.example.food_map.vo.FoodMapResponse;

@SpringBootTest
class FoodMapApplicationTests {

	@Autowired
	private FoodMapService foodMapService;

	@Test
	public void addStoreTest() {
		Store store = new Store("�ĥ|�a","�x�n",4);
		FoodMapResponse result = foodMapService.addStore(store);
		System.out.println(result.getMessage());
	}

	@Test
	public void addFoodTest() {
		List<Food> foodList = new ArrayList<>();
		foodList.add(new Food("��A�a","�M�d��",50,2.3));
		foodList.add(new Food("��A�a","������",150,4.2));
		foodList.add(new Food("��C�a","������",120,3.8));
		foodList.add(new Food("��B�a","��N��",80,4.6));
		foodList.add(new Food("��B�a","�ѽu�k",30,3.2));
		FoodMapResponse result = foodMapService.addFood(foodList);
		System.out.println(result.getMessage());
	}

	@Test
	public void addStoreAndFoodTest() {

	}

}
