package com.example.microservice1.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.microservice1.dto.EmployeeDTO;
import com.example.microservice1.dto.EmployeeResponseDTO;
import com.example.microservice1.dto.OrganisationDTO;
import com.example.microservice1.model.Employee;
import com.example.microservice1.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	private final ObjectMapper objectMapper;

	private final WebClient webClient;

	@Autowired
	public EmployeeServiceImpl(WebClient.Builder webClientBuilder, DiscoveryClient discoveryClient,
			ObjectMapper objectMapper) {
//		 Retrieve the URL of the organization service using Eureka
//		*****--- There is something wrong with my DNS settings that is preventing to access the url try this once and replace the baseUrl port with "organizationServiceUrl" 
//		String organizationServiceUrl = discoveryClient.getInstances("microservice2").stream().findFirst()
//				.map(instance -> instance.getUri().toString())
//				.orElseThrow(() -> new RuntimeException("Unable to retrieve organization service URL from Eureka"));

		this.webClient = webClientBuilder.baseUrl("http://localhost:9091").build();
		this.objectMapper = objectMapper;
	}
	

	@Override
	public void createEmployee(EmployeeDTO employeeRequest) throws JsonProcessingException {
		String jsonRequest = objectMapper.writeValueAsString(employeeRequest);
		Employee employee = objectMapper.readValue(jsonRequest, Employee.class);
		employeeRepository.save(employee);
	}

	@Override
	public Mono<EmployeeResponseDTO> getEmployeeById(int employeeId) {
		return employeeRepository.findById(employeeId)
	            .map(employee -> {
	                Mono<String> organizationNameMono = this.webClient
	                        .get()
	                        .uri("/organisation/{id}", employee.getOrganisationId())
	                        .retrieve()
	                        .bodyToMono(OrganisationDTO.class)
	                        .map(OrganisationDTO::getName);
	                return organizationNameMono.map(organizationName ->
	                        new EmployeeResponseDTO(employee.getName(), employee.getAddress(), employee.getDesignation(), organizationName)
	                );
	            })
	            .orElseThrow(() -> new RuntimeException("No Employee found with the given id"));
	}

	@Override
	public void updateEmployee(EmployeeDTO employeeDTO) throws JsonProcessingException {
		Employee existingEmployee = employeeRepository.findById(employeeDTO.getId())
				.orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeDTO.getId()));
		
		existingEmployee.setName(employeeDTO.getName());
		existingEmployee.setAddress(employeeDTO.getAddress());
		existingEmployee.setDesignation(employeeDTO.getDesignation());
		existingEmployee.setOrganisationId(employeeDTO.getOrganisationId());
		
		employeeRepository.save(existingEmployee);
	}
	@Override
public void deleteEmployee(int id) {
    if (!employeeRepository.existsById(id)) {
        throw new RuntimeException("Employee not found with id: " + id);
    }
    employeeRepository.deleteById(id);
}
	@Override
	public List<EmployeeDTO> getAllEmployees() {
		return employeeRepository.findAll().stream().map(employee -> {
			EmployeeDTO dto = new EmployeeDTO();
			dto.setId(employee.getId());
			dto.setName(employee.getName());
			dto.setAddress(employee.getAddress());
			dto.setDesignation(employee.getDesignation());
			dto.setOrganisationId(employee.getOrganisationId());
			return dto;
		}).collect(Collectors.toList());
	}

	
}
