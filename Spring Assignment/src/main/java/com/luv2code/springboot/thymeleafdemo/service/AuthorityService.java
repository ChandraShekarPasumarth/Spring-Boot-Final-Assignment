package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.entity.Admin;
import com.luv2code.springboot.thymeleafdemo.entity.Authority;

import java.util.List;

public interface AuthorityService {
    List<Authority> findAll();


}
