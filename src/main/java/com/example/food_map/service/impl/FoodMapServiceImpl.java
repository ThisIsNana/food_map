package com.example.food_map.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.food_map.constants.RtnCode;
import com.example.food_map.entity.Food;
import com.example.food_map.entity.Store;
import com.example.food_map.repository.FoodDAO;
import com.example.food_map.repository.StoreDAO;
import com.example.food_map.service.ifs.FoodMapService;
import com.example.food_map.vo.FoodMapResponse;

@Service
public class FoodMapServiceImpl implements FoodMapService {

	@Autowired
	private FoodDAO foodDAO;

	@Autowired
	private StoreDAO storeDAO;

	@Override // 新增店家
	public FoodMapResponse addStore(Store store) {

		// 防呆
		if (store == null) {
			return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		if (!StringUtils.hasText(store.getName()) || !StringUtils.hasText(store.getCity())) {
			return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}

		// 檢查重複
		Optional<Store> storeOp = storeDAO.findById(store.getName());
		if (storeOp.isPresent()) {
			return new FoodMapResponse(RtnCode.ALREADY_PRESENT.getMessage());
		}
		store.setRating(0);
		storeDAO.save(store);
		return new FoodMapResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override // 新增餐點
	public FoodMapResponse addFood(List<Food> foodList) {
		
		//防呆
		if (foodList == null) {
			return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		List<Food> foodSaveList = new ArrayList<>();
		List<String> storeNameList = new ArrayList<>();
		
		for (Food food : foodList) {
			if (!StringUtils.hasText(food.getFoodName()) || !StringUtils.hasText(food.getStoreName())) {
//				return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
				continue;
			}
			if (food.getRating() <= 0 || food.getRating() > 5 || food.getPrice() <= 0) {
//				return new FoodMapResponse(RtnCode.DATA_ERROR.getMessage());
				continue;
			}
			foodSaveList.add(food);
			storeNameList.add(food.getStoreName());
		}
		
		// 所有資料都有誤的話 => 顯示格式錯誤
		if (CollectionUtils.isEmpty(foodSaveList)) {
			return new FoodMapResponse(RtnCode.DATA_ERROR.getMessage());
		}
		
		// 用storeName尋找資料
		List<Store> resultStoreList = storeDAO.findAllById(storeNameList);
		
		
		// 查無存在的店家資料
		if (CollectionUtils.isEmpty(resultStoreList)) {
			return new FoodMapResponse(RtnCode.NOT_FOUND.getMessage());
		}

		List<Store> storeSaveList = new ArrayList<>();
		Double ratingSum = 0.0; // 評價總和
		
		//比對餐點並計算評價
		for (Store store : resultStoreList) { //A、B、A
			for (Food food : foodList) {	//Aa3、Bb2、Ab4
				if (store.getName().equals(food.getStoreName())) { 
					ratingSum += food.getRating();
				}
			}
			Double storeRating = ratingSum /2;
			store.setRating(storeRating);
			storeSaveList.add(store);
		}
		
		// 只新增沒有錯誤的資料
		foodDAO.saveAll(foodSaveList);
		storeDAO.saveAll(storeSaveList);
		return new FoodMapResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override // 同時新增餐點及店家
//	public FoodMapResponse addStoreAndFood(Store store, List<Food> foodList) {
	public FoodMapResponse addStoreAndFood(String storeName, String city, String foodName, int price, double foodRating) {

		// 防呆
		if(!StringUtils.hasText(storeName) 
				|| !StringUtils.hasText(storeName)
				|| !StringUtils.hasText(foodName)){
			return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		if(price <= 0 || foodRating < 1 || foodRating > 5) {
			return new FoodMapResponse(RtnCode.DATA_ERROR.getMessage());
		}
		
		/*
		 * 先用DAO去確認是否店家已經存在
		 * 不存在 → 新增店家及餐點
		 * 存在 → 檢查店家是否已經有三個餐點 → 檢查餐點是否重複 → 否，新增餐點
		 */
		
		Optional<Store> opSt = storeDAO.findById(storeName);
		if(!opSt.isPresent()) {
			Store newStore = new Store(storeName,city,foodRating);
			Food newFood = new Food(storeName,foodName,price,foodRating);
			storeDAO.save(newStore);
			foodDAO.save(newFood);
			return new FoodMapResponse(RtnCode.SUCCESSFUL.getMessage());
		}

		// 先預設要上傳給Store的評價是零。
		int storeRating = 0;
		List<Food> list = foodDAO.findAllByStoreName(storeName);
		if(list.size() == 3) {
			return new FoodMapResponse(RtnCode.CANNOT_UPDATE.getMessage());
		}
		
		Food resFood = foodDAO.findByIdStoreNameAndIdFoodName(storeName, foodName);
		if(resFood == null) {
			return new FoodMapResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		
		
		
		return new FoodMapResponse(RtnCode.SUCCESSFUL.getMessage());
	}

}
