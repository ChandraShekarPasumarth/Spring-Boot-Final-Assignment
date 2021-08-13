package com.luv2code.springboot.thymeleafdemo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Admin {

    @Id
    @NotEmpty(message = "Enter Username")
    @Pattern(regexp = "^[A-Za-z ]*$")
    private String username;

    @NotEmpty(message = "Enter Password")
    private String password;
    private int enabled;


    @OneToMany(fetch=FetchType.LAZY,
            cascade= CascadeType.ALL)
    @JoinColumn(name="username")
    private List<Authority> roles;



    public Admin() {
        this.roles = new ArrayList<>();
    }







}