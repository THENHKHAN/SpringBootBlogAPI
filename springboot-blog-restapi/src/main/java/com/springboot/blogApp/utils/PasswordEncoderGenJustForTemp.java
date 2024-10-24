package com.springboot.blogApp.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordEncoderGenJustForTemp {

    public static void main(String[] args) {
       PasswordEncoder pEncoder = new BCryptPasswordEncoder();
        String adminUserPassword = pEncoder.encode("admin");// $2a$10$fOtu0DxJvdhRU0oCBT12OuRNXwVhrgmuD2xvZ/XcKqkRvd86Gu1Vm
        String normalUserPassword = pEncoder.encode("nh12"); // $2a$10$Ne1vU076ReyNLboP5gg2Z.NwzlJ0N2BCVaZfvnMrzFP4obDIiMCY6
        System.out.println("Admin -> " + adminUserPassword);
        System.out.println("User -> "+ normalUserPassword);

    }
}
