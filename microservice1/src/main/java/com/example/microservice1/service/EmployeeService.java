package com.example.microservice1.service;

import com.example.microservice1.dto.EmployeeDTO;
import com.example.microservice1.dto.EmployeeResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import reactor.core.publisher.Mono;

public interface EmployeeService {

	public void createEmployee(EmployeeDTO employee) throws JsonProcessingException;

	public Mono<EmployeeResponseDTO> getEmployeeById(int employeeId);

    public void updateEmployee(EmployeeDTO employee) throws JsonProcessingException;

    public void deleteEmployee(int id);

    public Object getAllEmployees();



}
