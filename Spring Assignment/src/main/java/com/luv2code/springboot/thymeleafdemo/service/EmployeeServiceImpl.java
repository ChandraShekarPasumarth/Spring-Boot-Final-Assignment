package com.luv2code.springboot.thymeleafdemo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.luv2code.springboot.thymeleafdemo.dao.AuthorityRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Authority;
import com.luv2code.springboot.thymeleafdemo.exception.EmployeeErrorResponse;
import com.luv2code.springboot.thymeleafdemo.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.thymeleafdemo.dao.EmployeeRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Service
@RequestMapping("/employees")
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;


	@Autowired
	private AuthorityRepository authorityService;;
	
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

	@GetMapping("/list")
	public String listEmployees(Model theModel) {
		return "redirect:/employees/list/1";
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






