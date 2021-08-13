package com.luv2code.springboot.thymeleafdemo;

import com.luv2code.springboot.thymeleafdemo.dao.EmployeeRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThymeleafdemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void testGetEmployees() {
		List<Employee> employeesList =(List<Employee>) employeeRepository.findAll();
		int initialSize = employeesList.size();

		Employee employee = new Employee();
		employee.setFirstName("Gary");
		employee.setLastName("Wilson");
		employee.setEmail("gary@gmail.com");
		employee.setManagerName("Manager");
		int id=employeeService.save(employee);

		List<Employee> updatedStudentList = employeeService.findAll();

		assertEquals(updatedStudentList.size(), initialSize + 1);
	}

	@Test
	public void testSaveEmployee() {
		Employee employee = new Employee();
		employee.setFirstName("Gary");
		employee.setLastName("Wilson");
		employee.setEmail("gary@gmail.com");
		employee.setManagerName("Manager");

		int id= employeeService.save(employee);

		Employee tempEmployee = employeeService.findById(id);

		assertEquals(tempEmployee.getId(), id );
		assertEquals(tempEmployee.getFirstName(), employee.getFirstName());
		assertEquals(tempEmployee.getLastName(), employee.getLastName());
		assertEquals(tempEmployee.getEmail(), employee.getEmail());
	}

	@Test
	public void testGetEmployee() {

		Employee employee = new Employee();
		employee.setFirstName("Gary");
		employee.setLastName("Wilson");
		employee.setEmail("gary@gmail.com");
		employee.setManagerName("Manager");

		int id = employeeService.save(employee);

		Employee tempEmployee = employeeService.findById(id);
		assertNotEquals(tempEmployee, null);
		assertEquals(tempEmployee.getId(), id);
	}

	@Test
	public void testDeleteEmployee() {

		Employee employee = new Employee();
		employee.setFirstName("Gary");
		employee.setLastName("Wilson");
		employee.setEmail("gary@gmail.com");
		employee.setManagerName("Manager");

		int id = employeeService.save(employee);

		employeeService.deleteById(id);
		Employee tempEmployee=null;
		try{
			tempEmployee = employeeService.findById(id);
		}
		catch (Exception e){
			assertEquals(e.getMessage(),"Did not find employee id - "+id);
		}


	}

	@Test
	public void testUpdateEmployee() {

		Employee employee = new Employee();
		employee.setFirstName("Gary");
		employee.setLastName("Wilson");
		employee.setEmail("gary@gmail.com");
		employee.setManagerName("Manager");

		int id = employeeService.save(employee);

		Employee tempEmployee = employeeService.findById(id);
		employee.setLastName("Marky");

		employeeService.save(employee);

		employee = employeeService.findById(id);

		assertEquals(employee.getLastName(), "Marky");

	}
}

