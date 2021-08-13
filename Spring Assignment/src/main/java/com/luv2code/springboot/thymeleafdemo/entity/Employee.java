package com.luv2code.springboot.thymeleafdemo.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="employee")
public class Employee {

	// define fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@NotEmpty(message = "Enter the First name")
	@Pattern(regexp = "^[A-Za-z ]*$")
	@Column(name="first_name")
	private String firstName;

	@NotEmpty(message = "Enter Last Name")
	@Pattern(regexp = "^[A-Za-z ]*$")
	@Column(name="last_name")
	private String lastName;

	@NotEmpty(message = "Enter Mail Id")
	@Email(message = "Enter vaild mail id")
	@Column(name="email")
	private String email;

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}


	private String  managerName;

// define constructors
	
	public Employee() {
		
	}
	
	public Employee(int id, String firstName, String lastName, String email,String managerName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.managerName=managerName;

	}


	public Employee(String firstName, String lastName, String email,String managerName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.managerName=managerName;
	}

	// define getter/setter
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// define tostring

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", managerName="+managerName+"]";
	}
		
}











