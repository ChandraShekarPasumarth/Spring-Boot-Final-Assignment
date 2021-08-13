package com.luv2code.springboot.thymeleafdemo.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Authority {

    @Id
    private String username;
    private String authority;



    public Authority(String username, List<Authority> roles) {}





}