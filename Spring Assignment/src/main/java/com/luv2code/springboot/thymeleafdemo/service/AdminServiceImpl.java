package com.luv2code.springboot.thymeleafdemo.service;


import com.luv2code.springboot.thymeleafdemo.dao.AdminRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;


@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    public EntityManager entityManager;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void save(Admin admin ){
        adminRepository.save(admin);
    }


    public Admin getByUserName(String userName) {
        Query theQuery = entityManager.createQuery("from Admin where username =:userName",Admin.class);
        theQuery.setParameter("userName",userName);
        List<Admin> theAdmin = theQuery.getResultList();

        return theAdmin.get(0);
    }
}