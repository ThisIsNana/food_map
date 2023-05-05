package com.example.food_map.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "food")
@IdClass(FoodId.class)
public class Food {

	@Id
	@Column(name = "store_name")
	private String storeName;

	@Id
	@Column(name = "food_name")
	private String foodName;

	@Column(name = "price")
	private int price;

	@Column(name = "rating")
	private double rating;

	public Food() {
		super();
	}

	public Food(String storeName, String foodName, int price, double rating) {
		super();
		this.storeName = storeName;
		this.foodName = foodName;
		this.price = price;
		this.rating = rating;
	}

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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
}
