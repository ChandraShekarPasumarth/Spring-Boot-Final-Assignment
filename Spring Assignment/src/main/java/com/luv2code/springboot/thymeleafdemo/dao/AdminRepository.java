package com.luv2code.springboot.thymeleafdemo.dao;

import com.luv2code.springboot.thymeleafdemo.entity.Admin;
import com.luv2code.springboot.thymeleafdemo.entity.Authority;
import org.apache.catalina.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.HashSet;


public interface AdminRepository extends JpaRepository<Admin, String> {



    // that's it ... no need to write any code LOL!

}
