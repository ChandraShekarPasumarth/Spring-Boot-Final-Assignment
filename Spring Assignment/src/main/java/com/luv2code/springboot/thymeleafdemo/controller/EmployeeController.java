package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.*;

import com.luv2code.springboot.thymeleafdemo.dao.AuthorityRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Authority;
import com.luv2code.springboot.thymeleafdemo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;

import javax.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeeController {


	private EmployeeService employeeService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private AuthorityRepository authorityService;;
	
	public EmployeeController(EmployeeService employeeService) {
		this. employeeService = employeeService;
	}
	

		@GetMapping("/list")
	public String listEmployees(Model theModel) {
		return "redirect:/employees/list/1";
		}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Employee theEmployee = new Employee();
		
		theModel.addAttribute("employee", theEmployee);

		List<Authority> authorities = authorityService.findAll();
		HashSet<String> mangerNames = new HashSet<>();
		for(int i=0;i<authorities.size();i++){
			Authority temp = authorities.get(i);
			if(temp.getAuthority().equals("ROLE_MANAGER")){
				mangerNames.add(temp.getUsername());
			}
		}

		System.out.println(mangerNames);
		theModel.addAttribute("managers", mangerNames);


		return "/employees/employee-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId,
									Model theModel) {
		
		// get the employee from the service
		Employee theEmployee = employeeService.findById(theId);
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", theEmployee);

		List<Authority> authorities = authorityService.findAll();
		HashSet<String> mangerNames = new HashSet<>();
		for(int i=0;i<authorities.size();i++){
			Authority temp = authorities.get(i);
			if(temp.getAuthority().equals("ROLE_MANAGER")){
				mangerNames.add(temp.getUsername());
			}
		}

		System.out.println(mangerNames);
		theModel.addAttribute("managers", mangerNames);

		// send over to our form
		return "/employees/employee-form";			
	}
	
	
	@PostMapping("/save")
	public String saveEmployee(@Valid @ModelAttribute("employee") Employee theEmployee , BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "/employees/employee-form";
		}

		// save the employee
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}

		if(theEmployee.getManagerName()==null)
		theEmployee.setManagerName(username);

		employeeService.save(theEmployee);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";
	}
	
	
	
	@GetMapping("/list/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
								Model model, SecurityContextHolderAwareRequestWrapper request) {
		int pageSize = 5;

		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize);

		List<Employee> listEmployees = page.getContent();
		List<Employee>  employeeList = employeeService.findAll();
		model.addAttribute("currentPage", pageNo);

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		System.out.println(username);

		List<Employee> employees = new ArrayList<>();
		if(request.isUserInRole("ROLE_ADMIN")){
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("employees", listEmployees);
			model.addAttribute("totalItems", page.getTotalElements());

		}
		else {

			int count=0;
			for (int i = 0; i < employeeList.size(); i++) {
				Employee employee = employeeList.get(i);
				if ((employee.getManagerName()).equals(username)) {
					count++;
					employees.add(employee);
				}
			}

			System.out.println(count);
			System.out.println(pageNo);
			try {
				List<Employee> employees1 = employees.subList(5 * (pageNo - 1), Math.min((5 * (pageNo - 1) + 5), employees.size()));
				model.addAttribute("employees", employees1);
				model.addAttribute("totalPages", ((count) / 5) + 1);
				model.addAttribute("totalItems", count);
			}
			catch(Exception e){
				System.out.println("Invalid Id Exception Handled:"+pageNo);
			}


		}
		model.addAttribute("authname", username);
		return "/employees/list-employees";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {

		
		// delete the employee
		employeeService.deleteById(theId);
		
		// redirect to /employees/list
		return "redirect:/employees/list";
		
	}
	
	@GetMapping("/search")
	public String delete(@RequestParam("employeeName") String theName,
						 Model theModel) {
		
		// delete the employee
		List<Employee> theEmployees = employeeService.searchBy(theName);
		
		// add to the spring model
		theModel.addAttribute("employees", theEmployees);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		theModel.addAttribute("authname", username);
		
		// send to /employees/list
		return "/employees/list-employees";
		
	}
	@GetMapping("/error")
	public String handleError(){
		return "error";
	}


}


















