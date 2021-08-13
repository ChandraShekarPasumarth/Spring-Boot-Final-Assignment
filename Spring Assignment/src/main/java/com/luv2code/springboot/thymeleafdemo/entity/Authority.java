package com.luv2code.springboot.thymeleafdemo.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="authorities")
public class Authority {

    @Id
    private String username;
    private String authority;


    public Authority(){

 }
    public Authority(String username, List<Authority> roles) {}

    public Authority(String username, String authority) {
        super();
        this.username = username;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "Authority [username=" + username + ", authority=" + authority + "]";
    }


}