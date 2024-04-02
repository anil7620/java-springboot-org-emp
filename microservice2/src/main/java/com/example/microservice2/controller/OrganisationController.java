package com.example.microservice2.controller;


import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.microservice2.dto.OrganisationDTO;
import com.example.microservice2.service.OrganisationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller; 


@Controller
public class OrganisationController {
	
	private final OrganisationService organisationService;

    OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }
    @GetMapping("/")
    public String index(Model model) {
        // Optional: Add a message to be displayed on the index page
        model.addAttribute("message", "This is the index page. The following functionalities are available:");
        return "index";
    }

    @PostMapping("/organisation")
    public String addOrganisation(@ModelAttribute OrganisationDTO organisationRequest, RedirectAttributes redirectAttributes) {
        try {
            organisationService.createOrganisation(organisationRequest);
            redirectAttributes.addFlashAttribute("message", "Organisation created successfully!");
            return "redirect:/organisations"; // Redirecting to the organisations listing page
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating organisation");
            return "organisationForm"; // Back to the form page with an error message
        }
    }
    @PostMapping("/organisation/{id}")
    public String updateOrganisation(@PathVariable("id") int organisationId, @ModelAttribute OrganisationDTO organisationDTO, RedirectAttributes redirectAttributes) {
    try {
        redirectAttributes.addFlashAttribute("message", "Organisation updated successfully!");
        return "redirect:/organisation/" + organisationId;
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Error updating organisation");
        return "organisationForm"; // Assuming you have a form for updates
    }
}

    
    @GetMapping("/organisation")
    public String showAddOrganisationForm(Model model) {
        model.addAttribute("organisation", new OrganisationDTO()); 
        return "organisationForm"; 
    }

	
@GetMapping("/organisation")
public String getOrganisation(@RequestParam("id") int organisationId, Model model) {
    try {
        OrganisationDTO response = organisationService.getOrganisationById(organisationId);
        model.addAttribute("organisation", response);
        return "organisationDetails";
    } catch (Exception e) {
        model.addAttribute("errorMessage", "Organisation not found");
        return "errorPage";
    }
}

    @PutMapping("/organisation/{id}")
    public ResponseEntity<OrganisationDTO> updateOrganisation(@PathVariable("id") int organisationId, @RequestBody OrganisationDTO organisationDTO) throws JsonProcessingException {
        OrganisationDTO updatedOrganisation = organisationService.updateOrganisation(organisationId, organisationDTO);
        if(updatedOrganisation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedOrganisation, HttpStatus.OK);
    }
    
    @GetMapping("/organisation/delete/{id}")
    public String deleteOrganisation(@PathVariable("id") int organisationId, RedirectAttributes redirectAttributes) {
        boolean isDeleted = organisationService.deleteOrganisation(organisationId);
        if(isDeleted) {
            redirectAttributes.addFlashAttribute("message", "Organisation deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Organisation not found or error deleting organisation");
        }
        return "redirect:/organisations";
    }

    @GetMapping("/organisation/operations")
public String organisationOperations() {
    return "organisation-operations"; // returns the Thymeleaf template name
}

    @GetMapping("/organisations")
    public String listOrganisations(Model model) {
        System.out.println("Fetching organisations...");
        List<OrganisationDTO> organisations = organisationService.getAllOrganisations();
        System.out.println("Organisations: " + organisations);
        model.addAttribute("organisations", organisations);
        return "organisations"; // Ensure this template exists
    }
    

        

}
