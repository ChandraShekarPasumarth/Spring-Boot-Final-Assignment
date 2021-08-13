package com.luv2code.springboot.thymeleafdemo.controller;


import com.luv2code.springboot.thymeleafdemo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller

public class AdminController {


	@Autowired
	private AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService =adminService;
	}




}


















