package com.example.microservice1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.microservice1.dto.EmployeeDTO;
import com.example.microservice1.dto.EmployeeResponseDTO;
import com.example.microservice1.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException; 
import reactor.core.publisher.Mono; 
import org.springframework.ui.Model;


@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	

	@GetMapping("/") 
    public String index() {
        return "index";
    }	

	@GetMapping("/employees")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees()); // Ensure you have this method in your service
        return "employees_list";
    }


    @PostMapping("/employee")
	public String addEmployee(@ModelAttribute EmployeeDTO employee, RedirectAttributes redirectAttributes) {
		try {
			employeeService.createEmployee(employee);
		} catch (JsonProcessingException e) { 
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "Employee added successfully!");
		return "redirect:/employees";
	}
	

	@GetMapping("/employee/new")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        return "employee_add";
    }

	
	@GetMapping("/employee/{id}")
	private Mono<ResponseEntity<EmployeeResponseDTO>>getEmployee(@PathVariable("id") int employeeId) throws JsonProcessingException {
		Mono<EmployeeResponseDTO> response = employeeService.getEmployeeById(employeeId);
		return response.map(ResponseEntity::ok);
	}
	
	@PostMapping("/employee/update")
    public String updateEmployee(@ModelAttribute EmployeeDTO employee, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        employeeService.updateEmployee(employee); // Ensure your service has this method
        redirectAttributes.addFlashAttribute("message", "Employee updated successfully!");
        return "redirect:/employees";
    }

	@GetMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployee(id); // Ensure your service has this method
        redirectAttributes.addFlashAttribute("message", "Employee deleted successfully!");
        return "redirect:/employees";
    }

    @GetMapping("/employee/edit/{id}")
    public String showEditEmployeeForm(@PathVariable("id") int id, Model model) {
        // Assuming getEmployeeById returns a Mono<EmployeeDTO>
        EmployeeResponseDTO employee = employeeService.getEmployeeById(id)
                                               .block(); // Block to unwrap the Mono, use with caution
        
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        
        model.addAttribute("employee", employee);
        return "employee_edit";
    }
    

    @PostMapping
    public ResponseEntity<Void> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            employeeService.createEmployee(employeeDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create employee", e);
        }
    }


	

}
