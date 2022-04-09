package com.springboot.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.employee.model.Employee;
import com.springboot.employee.repository.EmployeeRepository;


@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	//depencency injection: inject repository layer here
	private EmployeeRepository employeeRepository;	
	
	//constructor-based DI done with parameterized constructor
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}


	//implementation of abstract method to list all employees from db
	@Override
	public List<Employee> getAllEmployees() {		 
		return employeeRepository.findAll();
	}


	//implementation of abstract method to add new employee to db
	@Override
	public void saveEmployee(Employee employee) {
		this.employeeRepository.save(employee);
		
	}


	@Override
	public Employee getEmployeeById(long id) {
		 Optional<Employee> optional = employeeRepository.findById(id);
		 Employee employee = null;
		 if(optional.isPresent()) {
			 employee = optional.get();
		 }
		 else {
			 throw new RuntimeException("Employee not found for id:: " + id);
		 }
		return employee;
	}


	@Override
	public void deleteEmployeeById(long id) {
		this.employeeRepository.deleteById(id);
		
	}

	//implementation of abstract method to enable pagination and sorting
	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
				Sort.by(sortField).ascending() :
					Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);
		return this.employeeRepository.findAll(pageable);
	}

}
