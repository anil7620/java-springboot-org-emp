package com.example.microservice2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.microservice2.dto.OrganisationDTO;
import com.example.microservice2.model.Organisation;
import com.example.microservice2.repository.OrganisationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrganisationServiceImpl implements OrganisationService{
	
	private final OrganisationRepository organisationRepository;

	private final ObjectMapper objectMapper;

    public OrganisationServiceImpl(ObjectMapper objectMapper, OrganisationRepository organisationRepository) {
        this.objectMapper = objectMapper;
        this.organisationRepository = organisationRepository;
    }

	@Override
	public void createOrganisation(OrganisationDTO organisationRequest) throws JsonMappingException, JsonProcessingException {
		String jsonRequest = objectMapper.writeValueAsString(organisationRequest);
		Organisation organisation = objectMapper.readValue(jsonRequest, Organisation.class);
		organisationRepository.save(organisation);
		
	}

	@Override
	public OrganisationDTO getOrganisationById(int organisationId) throws JsonProcessingException {
		Optional<Organisation> organisation = organisationRepository.findById(organisationId);
		if(organisation.isPresent()) {
			String jsonResponse = objectMapper.writeValueAsString(organisation);
			return objectMapper.readValue(jsonResponse, OrganisationDTO.class);
		}
		throw new RuntimeException("No Record Found for the organisation");
	}
	
    @Override
    public OrganisationDTO updateOrganisation(int organisationId, OrganisationDTO organisationDTO) throws JsonProcessingException {
        Optional<Organisation> organisationOptional = organisationRepository.findById(organisationId);
        if (organisationOptional.isPresent()) {
            Organisation organisation = organisationOptional.get();
            organisation.setName(organisationDTO.getName());
            organisation.setNetValue(organisationDTO.getNetValue());
            organisation = organisationRepository.save(organisation);
            String jsonResponse = objectMapper.writeValueAsString(organisation);
            return objectMapper.readValue(jsonResponse, OrganisationDTO.class);
        } else {
            throw new RuntimeException("No Record Found for the organisation with ID: " + organisationId);
        }
    }

    
    @Override
    public boolean deleteOrganisation(int organisationId) {
        Optional<Organisation> organisation = organisationRepository.findById(organisationId);
        if (organisation.isPresent()) {
            organisationRepository.deleteById(organisationId);
            return true;
        } else {
            return false;
        }
    }

@Override
public List<OrganisationDTO> getAllOrganisations() {
    List<Organisation> organisations = organisationRepository.findAll();
    // Assuming you have a method to convert Organisation entities to DTOs
    return organisations.stream().map(this::convertToDTO).collect(Collectors.toList());
}

private OrganisationDTO convertToDTO(Organisation organisation) {
    OrganisationDTO dto = new OrganisationDTO();
    dto.setName(organisation.getName());
    dto.setNetValue(organisation.getNetValue());
    // Set other fields as necessary
    return dto;
}



}
