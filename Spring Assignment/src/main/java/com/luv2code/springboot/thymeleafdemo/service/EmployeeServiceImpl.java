package com.luv2code.springboot.thymeleafdemo.service;

import java.util.List;
import java.util.Optional;

import com.luv2code.springboot.thymeleafdemo.exception.EmployeeErrorResponse;
import com.luv2code.springboot.thymeleafdemo.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.thymeleafdemo.dao.EmployeeRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
@Component
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository) {
		employeeRepository = theEmployeeRepository;
	}
	
	@Override
	public List<Employee> findAll() {


		return employeeRepository.findAllByOrderByLastNameAsc();
	}

	@Override
	public Employee findById(int theId) {
		Optional<Employee> result = employeeRepository.findById(theId);


		Employee theEmployee = null;

		if (result.isPresent()) {
			theEmployee = result.get();
		} else {
			// we didn't find the employee
			throw new RuntimeException("Did not find employee id - " + theId);
		//	throw new EmployeeNotFoundException("Did not find employee id - " + theId);

		}

		return theEmployee;
	}
/*	//Add an Exception Handler
	@ExceptionHandler(Exception.class)
	public ResponseEntity<EmployeeNotFoundException> handleException(EmployeeNotFoundException exc){
		EmployeeErrorResponse error =  new EmployeeErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());

		//return responseEntity
		return new ResponseEntity<>((MultiValueMap<String, String>) error,HttpStatus.NOT_FOUND);
	}

 */



	@Override
	public int save(Employee theEmployee) {
		employeeRepository.save(theEmployee);
		return theEmployee.getId();
	}

	@Override
	public void deleteById(int theId) {

		employeeRepository.deleteById(theId);
	}

	@Override
	public List<Employee> searchBy(String theName) {
		
		List<Employee> results = null;
		
		if (theName != null && (theName.trim().length() > 0)) {
			results = employeeRepository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(theName, theName);
		}
		else {
			results = findAll();
		}
		
		return results;
	}

	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
	return this.employeeRepository.findAll(pageable);
	}




}






