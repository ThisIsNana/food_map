package com.example.food_map.entity;

import java.io.Serializable;

public class FoodId implements Serializable{

	private static final long serialVersionUID = 1L;

	private String storeName;

	private String foodName;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
