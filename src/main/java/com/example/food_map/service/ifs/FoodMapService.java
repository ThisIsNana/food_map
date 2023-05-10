package com.example.food_map.service.ifs;

import java.util.List;

import com.example.food_map.entity.Food;
import com.example.food_map.entity.Store;
import com.example.food_map.vo.FoodMapResponse;

public interface FoodMapService {
//  1.新增店家/餐點
//	2.搜尋特定城市+限制筆數
//	3.搜尋店家評價X星以上
//	4.搜尋店家評價X星以上+餐點X星以上
//	5.刪除店家及其所有餐點
	
	//add food and store
	public FoodMapResponse addStore(Store store);
	public FoodMapResponse addFood(List<Food> foodList);
//	public FoodMapResponse addStoreAndFood(Store store, List<Food> foodList);
	public FoodMapResponse addStoreAndFood(String storeName, String city, String foodName, int price, double rating);
}
