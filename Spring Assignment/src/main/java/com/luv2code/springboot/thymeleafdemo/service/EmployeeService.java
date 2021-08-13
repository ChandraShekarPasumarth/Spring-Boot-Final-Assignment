package com.luv2code.springboot.thymeleafdemo.service;

import java.util.List;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public interface EmployeeService {

	public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public int save(Employee theEmployee);
	
	public void deleteById(int theId);

	public List<Employee> searchBy(String theName);

	Page<Employee> findPaginated(int pageNo, int pageSize);

}
