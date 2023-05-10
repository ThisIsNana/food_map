package com.example.food_map.service.ifs;

import java.util.List;

import com.example.food_map.entity.Food;
import com.example.food_map.entity.Store;
import com.example.food_map.vo.FoodMapResponse;

public interface FoodMapService {
//  1.�s�W���a/�\�I
//	2.�j�M�S�w����+�����
//	3.�j�M���a����X�P�H�W
//	4.�j�M���a����X�P�H�W+�\�IX�P�H�W
//	5.�R�����a�Ψ�Ҧ��\�I
	
	//add food and store
	public FoodMapResponse addStore(Store store);
	public FoodMapResponse addFood(List<Food> foodList);
//	public FoodMapResponse addStoreAndFood(Store store, List<Food> foodList);
	public FoodMapResponse addStoreAndFood(String storeName, String city, String foodName, int price, double rating);
}
