package com.springboot.blogApp.controller;


import com.springboot.blogApp.payload.JwtAuthResponse;
import com.springboot.blogApp.payload.LoginDto;
import com.springboot.blogApp.payload.RegisterDto;
import com.springboot.blogApp.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Auth Controller", description = "Auth Controller exposes SignIn and SignUp REST APIs")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Value("${app.jwt-expiration-millisecond}") // getting token expiration time from application.properties
    private long jwtExpirationInMs;

//    Build Register REST API : POST  http://localhost:8080/api/auth/register OR http://localhost:8080/api/auth/signup
    @Operation(summary = "REST API to Register/SignUp User", description = " Register new user for Blog App ")
    @PostMapping(value = {"/register", "/signup"}) // multiple url we used value attribute.
    public ResponseEntity<String> register( @RequestBody RegisterDto registerDto){

        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


//    Build Login REST API : POST  http://localhost:8080/api/auth/login OR http://localhost:8080/api/auth/signin
    @Operation(summary = "REST API to Login/SignIn User", description = " Login existing user for Blog App and get JWT Token")
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();

        String jwtToken = authService.login(loginDto);

        jwtAuthResponse.setAccess_token(jwtToken);

        jwtAuthResponse.setExpires_in(jwtExpirationInMs);

        return ResponseEntity.ok(jwtAuthResponse);
        }
}
