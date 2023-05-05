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
	
	@Override  //新增店家
	public FoodMapResponse addStore(Store store) {
		//防呆
		if(store == null) {
			return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		if(!StringUtils.hasText(store.getName()) || !StringUtils.hasText(store.getCity())) {
			return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		//檢查重複
		Optional<Store> storeOp = storeDAO.findById(store.getName());
		if(storeOp.isPresent()) {
			return new FoodMapResponse(RtnCode.ALREADY_PRESENT.getMessage());
		}
		store.setRating(0);
		storeDAO.save(store);
		return new FoodMapResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override  //新增餐點
	public FoodMapResponse addFood(List<Food> foodList ) {
		if(foodList == null) {
			return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		List<Food> foodSaveList = new ArrayList<>();
		List<String> storeNameList = new ArrayList<>();
		for(Food food : foodList) {
			if(!StringUtils.hasText(food.getFoodName()) || !StringUtils.hasText(food.getStoreName())) {
//				return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
				continue;
			}
			if(food.getRating() <= 0 || food.getRating() > 5 || food.getPrice() <= 0) {
//				return new FoodMapResponse(RtnCode.DATA_ERROR.getMessage());
				continue;
			}
			foodSaveList.add(food);
			storeNameList.add(food.getStoreName());
		}
		//所有資料都有誤的話 => 顯示格式錯誤
		if(CollectionUtils.isEmpty(foodSaveList)) {
			return new FoodMapResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//更新評價計算
		List<Store> resultStoreList = storeDAO.findAllById(storeNameList);
		List<Store> storeSaveList = new ArrayList<>();
		//查無存在的店家資料
		if(CollectionUtils.isEmpty(resultStoreList)) {
			return new FoodMapResponse(RtnCode.NOT_FOUND.getMessage());
		}
		Double ratingSum = 0.0; //評價總和
		int storeFoodCount = 0;
		for(Store store : resultStoreList) {
			storeFoodCount = 0;
			for(Food food : foodList) {
				if(store.getName().equals(food.getStoreName())) {
					storeFoodCount++;
					ratingSum += food.getRating();
				}
			}
			Double storeRating = ratingSum / storeFoodCount;
			store.setRating(storeRating);
			storeSaveList.add(store);
		}
		//只新增沒有錯誤的資料
		foodDAO.saveAll(foodSaveList);	
		storeDAO.saveAll(storeSaveList);
		return new FoodMapResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override  //同時新增餐點及店家
	public FoodMapResponse addStoreAndFood(Store store, List<Food> foodList) {
		//店家的部分
		//防呆
		if(store == null) {
			return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		if(!StringUtils.hasText(store.getName()) || !StringUtils.hasText(store.getCity())) {
			return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		//檢查重複
		Optional<Store> storeOp = storeDAO.findById(store.getName());
		if(storeOp.isPresent()) {
			return new FoodMapResponse(RtnCode.ALREADY_PRESENT.getMessage());
		}
		store.setRating(0);
		storeDAO.save(store);
		//食物的部分
		//防呆
		if(foodList == null) {
			return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		List<Food> saveList = new ArrayList<>();
		List<String> storeNameList = new ArrayList<>();
		for(Food food : foodList) {
			if(!StringUtils.hasText(food.getFoodName()) || !StringUtils.hasText(food.getStoreName())) {
//				return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
				continue;
			}
			if(food.getRating() <= 0 || food.getRating() > 5 || food.getPrice() <= 0) {
//				return new FoodMapResponse(RtnCode.DATA_ERROR.getMessage());
				continue;
			}
			saveList.add(food);
			storeNameList.add(food.getStoreName());
		}
		//所有資料都有誤的話 => 顯示格式錯誤
		if(CollectionUtils.isEmpty(saveList)) {
			return new FoodMapResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//更新評價計算
		List<Store> resultStoreList = storeDAO.findAllById(storeNameList);
		if(CollectionUtils.isEmpty(resultStoreList)) {
			return new FoodMapResponse(RtnCode.NOT_FOUND.getMessage());
		}
		int ratingSum = 0; //評價總和
		for(Store resultStore : resultStoreList) {
			for(Food food : foodList) {
				if(resultStore.getName().equals(food.getStoreName())) {
					ratingSum += food.getRating();
				}
			}
		}
		//只新增沒有錯誤的資料
		foodDAO.saveAll(saveList);	
		return new FoodMapResponse(RtnCode.SUCCESSFUL.getMessage());
	}


}
