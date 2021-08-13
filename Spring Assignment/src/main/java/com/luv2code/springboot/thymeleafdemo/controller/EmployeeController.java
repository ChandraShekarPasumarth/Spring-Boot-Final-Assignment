package com.luv2code.springboot.thymeleafdemo.controller;


import org.springframework.stereotype.Controller;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;

@Controller
public class EmployeeController {


	private EmployeeService employeeService;

	
	public EmployeeController(EmployeeService employeeService) {
		this. employeeService = employeeService;
	}


}


















