package com.springboot.employee.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.springboot.employee.model.Employee;

public interface EmployeeService {

	//abstract method to return all employee data in list
	List<Employee> getAllEmployees();
	
	//abstract method to add new employee
	void saveEmployee(Employee employee);
	
	//abstract method to retrieve an employee by its id, it's needed for update feature
	Employee getEmployeeById(long id);
	
	//abstract method to delete an employee form db
	void deleteEmployeeById(long id);
	
	//abstract method to add pagination and sorting
	Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
