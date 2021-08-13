package com.luv2code.springboot.thymeleafdemo.service;


import com.luv2code.springboot.thymeleafdemo.dao.AdminRepository;
import com.luv2code.springboot.thymeleafdemo.dao.AuthorityRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Admin;
import com.luv2code.springboot.thymeleafdemo.entity.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
@RequestMapping("/admins")
public class AdminServiceImpl implements AdminService{

    @Autowired
    public EntityManager entityManager;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AuthorityRepository authorityService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @GetMapping("/showManagers")
    public String showManagers(Model theModel) {

        // create model attribute to bind form data
        Admin admin = new Admin();


        theModel.addAttribute("admin", admin);

        List<Authority> authorities = authorityService.findAll();
        //System.out.println(authorities);
        HashSet<String> mangerNames = new HashSet<>();
        for(int i=0;i<authorities.size();i++){
            Authority temp = authorities.get(i);
            if(temp.getAuthority().equals("ROLE_MANAGER")){
                mangerNames.add(temp.getUsername());
            }
        }

        System.out.println(mangerNames);
        theModel.addAttribute("managers", mangerNames);

        return "/employees/list-manager";
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("admin") Admin admin, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "/employees/admin-form";
        }
        List<Authority> authorities=new ArrayList<Authority>();
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        System.out.println(admin.getPassword());
        Authority manager = new Authority(admin.getUsername(),"ROLE_MANAGER");
        authorities.add(manager);
        System.out.println(admin.getPassword());
        System.out.println(admin.getUsername());
        admin.setEnabled(1);
        admin.setRoles(authorities);
        System.out.println(admin.getRoles());
        adminService.save(admin);
        return "redirect:/employees/list";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Admin admin = new Admin();

        theModel.addAttribute("admin", admin);

        return "/employees/admin-form";
    }

}