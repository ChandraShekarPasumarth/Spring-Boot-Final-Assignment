package com.luv2code.springboot.thymeleafdemo.service;


import com.luv2code.springboot.thymeleafdemo.dao.AuthorityRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Admin;
import com.luv2code.springboot.thymeleafdemo.entity.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.HashMap;
import java.util.List;

@Service
@RequestMapping("/managers")
public class AuthorityServiceImpl implements AuthorityService{


    @Autowired
    public AdminServiceImpl adminService;


    @Autowired
     public  AuthorityRepository authorityRepository;
    @Override
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }


    @GetMapping("/showPasswordUpdate")
    public String showFormPasswordUpdate(Model model){
        HashMap<String,String> theMap = new HashMap<>();
        model.addAttribute("oldPassword", new String());
        model.addAttribute("newPassword", new String());

        return"/employees/password-form";
    }

    @PostMapping("/passwordUpdate")
    public String passwordUpdate(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword){
        System.out.println(oldPassword);
        System.out.println(newPassword);
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();
        System.out.println("Login "+userName);

        Admin theAdmin = adminService.getByUserName(userName);
        System.out.println(theAdmin.getPassword());

        String compare = theAdmin.getPassword().substring(8);


//        System.out.println("Password from Bcrypt");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//        System.out.print(encodedPassword);
        if( passwordEncoder.matches(oldPassword,compare)){
            System.out.println("Old Password is correct updating new password");
            String encodedPassword ="{bcrypt}"+passwordEncoder.encode(newPassword);
            theAdmin.setPassword(encodedPassword);
            System.out.println("Hello world");
            adminService.save(theAdmin);
        }
        else{
            return "redirect:/showPasswordUpdate";
        }

        return "redirect:/employees/list";
    }


}
