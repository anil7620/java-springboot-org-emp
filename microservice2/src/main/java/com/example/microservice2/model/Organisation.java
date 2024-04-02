package com.example.microservice2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Organisation {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int id;
	
	private String name;
	
	private double netValue;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getNetValue() {
		return netValue;
	}

	public void setNetValue(double netValue) {
		this.netValue = netValue;
	}

	@Override
	public String toString() {
		return "Organisation [id=" + id + ", name=" + name + ", netValue=" + netValue + "]";
	}
	
	

}
