package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.dao.AuthorityRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Admin;
import com.luv2code.springboot.thymeleafdemo.entity.Authority;
import com.luv2code.springboot.thymeleafdemo.service.AdminService;
import com.luv2code.springboot.thymeleafdemo.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityRepository authorityService;

	@Autowired
	private AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService =adminService;
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Admin admin = new Admin();
		
		theModel.addAttribute("admin", admin);
		
		return "/employees/admin-form";
	}

	@GetMapping("/showManagers")
	public String showManagers(Model theModel) {

		// create model attribute to bind form data
		Admin admin = new Admin();


		theModel.addAttribute("admin", admin);

		List<Authority> authorities = authorityService.findAll();
		//System.out.println(authorities);
		HashSet<String> mangerNames = new HashSet<>();
		for(int i=0;i<authorities.size();i++){
			Authority temp = authorities.get(i);
			if(temp.getAuthority().equals("ROLE_MANAGER")){
				mangerNames.add(temp.getUsername());
			}
		}

		System.out.println(mangerNames);
		theModel.addAttribute("managers", mangerNames);

		return "/employees/list-manager";
	}

	
	@PostMapping("/save")
	public String saveEmployee(@Valid  @ModelAttribute("admin") Admin admin,BindingResult bindingResult) {

			if(bindingResult.hasErrors()){
				return "/employees/admin-form";
			}
        List<Authority> authorities=new ArrayList<Authority>();
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		System.out.println(admin.getPassword());
        Authority manager = new Authority(admin.getUsername(),"ROLE_MANAGER");
		authorities.add(manager);
		System.out.println(admin.getPassword());
		System.out.println(admin.getUsername());
		admin.setEnabled(1);
		admin.setRoles(authorities);
		System.out.println(admin.getRoles());
		adminService.save(admin);
		return "redirect:/employees/list";
	}


}


















