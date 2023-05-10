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

	@Override // �s�W���a
	public FoodMapResponse addStore(Store store) {

		// ���b
		if (store == null) {
			return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		if (!StringUtils.hasText(store.getName()) || !StringUtils.hasText(store.getCity())) {
			return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}

		// �ˬd����
		Optional<Store> storeOp = storeDAO.findById(store.getName());
		if (storeOp.isPresent()) {
			return new FoodMapResponse(RtnCode.ALREADY_PRESENT.getMessage());
		}
		store.setRating(0);
		storeDAO.save(store);
		return new FoodMapResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override // �s�W�\�I
	public FoodMapResponse addFood(List<Food> foodList) {
		
		//���b
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
		
		// �Ҧ���Ƴ����~���� => ��ܮ榡���~
		if (CollectionUtils.isEmpty(foodSaveList)) {
			return new FoodMapResponse(RtnCode.DATA_ERROR.getMessage());
		}
		
		// ��storeName�M����
		List<Store> resultStoreList = storeDAO.findAllById(storeNameList);
		
		
		// �d�L�s�b�����a���
		if (CollectionUtils.isEmpty(resultStoreList)) {
			return new FoodMapResponse(RtnCode.NOT_FOUND.getMessage());
		}

		List<Store> storeSaveList = new ArrayList<>();
		Double ratingSum = 0.0; // �����`�M
		
		//����\�I�íp�����
		for (Store store : resultStoreList) { //A�BB�BA
			for (Food food : foodList) {	//Aa3�BBb2�BAb4
				if (store.getName().equals(food.getStoreName())) { 
					ratingSum += food.getRating();
				}
			}
			Double storeRating = ratingSum /2;
			store.setRating(storeRating);
			storeSaveList.add(store);
		}
		
		// �u�s�W�S�����~�����
		foodDAO.saveAll(foodSaveList);
		storeDAO.saveAll(storeSaveList);
		return new FoodMapResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override // �P�ɷs�W�\�I�Ω��a
//	public FoodMapResponse addStoreAndFood(Store store, List<Food> foodList) {
	public FoodMapResponse addStoreAndFood(String storeName, String city, String foodName, int price, double foodRating) {

		// ���b
		if(!StringUtils.hasText(storeName) 
				|| !StringUtils.hasText(storeName)
				|| !StringUtils.hasText(foodName)){
			return new FoodMapResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		if(price <= 0 || foodRating < 1 || foodRating > 5) {
			return new FoodMapResponse(RtnCode.DATA_ERROR.getMessage());
		}
		
		/*
		 * ����DAO�h�T�{�O�_���a�w�g�s�b
		 * ���s�b �� �s�W���a���\�I
		 * �s�b �� �ˬd���a�O�_�w�g���T���\�I �� �ˬd�\�I�O�_���� �� �_�A�s�W�\�I
		 */
		
		Optional<Store> opSt = storeDAO.findById(storeName);
		if(!opSt.isPresent()) {
			Store newStore = new Store(storeName,city,foodRating);
			Food newFood = new Food(storeName,foodName,price,foodRating);
			storeDAO.save(newStore);
			foodDAO.save(newFood);
			return new FoodMapResponse(RtnCode.SUCCESSFUL.getMessage());
		}

		// ���w�]�n�W�ǵ�Store�������O�s�C
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
