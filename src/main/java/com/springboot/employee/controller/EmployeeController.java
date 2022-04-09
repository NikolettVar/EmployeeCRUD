package com.springboot.employee.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.employee.model.Employee;
import com.springboot.employee.service.EmployeeService;


@Controller
public class EmployeeController {
	
	//DI first, inject service layer here
	private EmployeeService employeeService;

	//parameterized constructor for DI
	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	//handler method will display list of employees when index.html is visited
	@GetMapping("/")
	public String viewHomePage(Model model) {
		//model.addAttribute("listEmployees", employeeService.getAllEmployees());
		
		//an html view is returned, it will be created as a Thymeleaf template
		//SB automatically creates a view resolver when thymeleaf dependency is present in pom file
		//return "index"; 
		
		//call handler method for pagination defined below
		return findPaginated(1, "firstName", "asc", model);
	}
	
	//handler method will display a form to create new employee object
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model){
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee"; //return a form to fill out	
			
	}
	
	//when new employee data is entered into the form, this method saves data
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/";
	}
	
	//a handler method to handle 'Update' http request, it will show a form
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value="id") long id, Model model) {
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	//a handler method to handle delete employee http request
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value="id") long id) {
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/";
	}
	
	//a handler method to handle pagination 
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value="pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5; //we use fixed page size here
		
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listEmployees", listEmployees);
		
		return "index";
	}
	

}
