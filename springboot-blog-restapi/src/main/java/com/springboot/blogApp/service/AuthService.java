package com.springboot.blogApp.service;

import com.springboot.blogApp.payload.LoginDto;
import com.springboot.blogApp.payload.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    String login (LoginDto loginDto);
}
