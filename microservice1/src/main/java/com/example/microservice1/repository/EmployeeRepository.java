package com.example.microservice1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservice1.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
