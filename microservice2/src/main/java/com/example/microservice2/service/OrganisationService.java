package com.example.microservice2.service;

import java.util.List;

import com.example.microservice2.dto.OrganisationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


public interface OrganisationService {

	void createOrganisation(OrganisationDTO organisationRequest) throws JsonMappingException, JsonProcessingException;

	OrganisationDTO getOrganisationById(int organisationId) throws JsonProcessingException;
	
	
    OrganisationDTO updateOrganisation(int organisationId, OrganisationDTO organisationDTO) throws JsonProcessingException;
    
    
    boolean deleteOrganisation(int organisationId);
// In OrganisationService.java
List<OrganisationDTO> getAllOrganisations();


}
