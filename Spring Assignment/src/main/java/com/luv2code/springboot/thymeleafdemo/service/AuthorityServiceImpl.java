package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.dao.AdminRepository;
import com.luv2code.springboot.thymeleafdemo.dao.AuthorityRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Admin;
import com.luv2code.springboot.thymeleafdemo.entity.Authority;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class AuthorityServiceImpl implements AuthorityService{



    @Autowired
  public  AuthorityRepository authorityRepository;
    @Override
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }




}
