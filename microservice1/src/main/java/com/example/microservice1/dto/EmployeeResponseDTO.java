package com.example.microservice1.dto;

public class EmployeeResponseDTO {

	public EmployeeResponseDTO(String name, String address, String designation, String organisationName) {
		super();
		this.name = name;
		this.address = address;
		this.designation = designation;
		this.organisationName = organisationName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	private String name;

	private String address;
	
	private String designation;
	
	private  String organisationName;
	
}
