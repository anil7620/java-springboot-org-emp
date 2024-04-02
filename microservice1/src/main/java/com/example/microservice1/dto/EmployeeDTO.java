package com.example.microservice1.dto;

public class EmployeeDTO {
	
	private int id;
	
	private String name;

	private String address;
	
	private String designation;

	private int organisationId;

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

	public int getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(int organisationId) {
		this.organisationId = organisationId;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
