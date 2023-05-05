package com.example.food_map.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "store")
public class Store {

	@Id
	@Column(name = "name")
	private String name;

	@Column(name = "city")
	private String city;

	@Column(name = "rating")
	private double rating;

	public Store() {
		super();
	}

	public Store(String name, String city) {
		super();
		this.name = name;
		this.city = city;
	}

	public Store(String name, String city, double rating) {
		super();
		this.name = name;
		this.city = city;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

}
