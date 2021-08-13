package com.luv2code.springboot.thymeleafdemo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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



	@Column(name="manager_name")
	private String  managerName;



	public Employee(String firstName, String lastName, String email,String managerName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.managerName=managerName;
	}


		
}











