package com.springboot.blogApp.service.impl;

import com.springboot.blogApp.customExceptions.BlogAPIException;
import com.springboot.blogApp.models.Role;
import com.springboot.blogApp.models.User;
import com.springboot.blogApp.payload.LoginDto;
import com.springboot.blogApp.payload.RegisterDto;
import com.springboot.blogApp.respository.RoleRepository;
import com.springboot.blogApp.respository.UserRepository;
import com.springboot.blogApp.security.JwtTokenProvider;
import com.springboot.blogApp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
   private RoleRepository roleRepository;

   private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

   @Autowired // mandatory
   private AuthenticationManager authenticationManager;

//   will inject JwtTokenProvider utility class based Annotation : Since it provide all useful functions
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    //    Registering Service handler
    @Override
    public String register(RegisterDto registerDto) {

//        Let's add some validation: Whether username already exists in DB.
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already Exists in Database!.");
        }

//        Let's check validation for email already exists in DB.
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "User Email is already Exists in Database!.");
        }

//        now if both thea above passed then will go this : for registering user.
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
//        System.out.println("PASSWORD : "+ registerDto.getPassword());
//        System.exit(1);
//        System.out.println("ENCODED PASSWORD : "+ passwordEncoder.encode(registerDto.getPassword()));
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
//        for role setting
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("USER");
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return String.format("User of username: %s registered Successfully ", registerDto.getUsername());
    }



    //   Login Service handler registered user

    @Override
    public String login(LoginDto loginDto) {
        System.out.println("in login function ");
        System.out.println("getUsername from client : " + loginDto.getUsername());
        System.out.println("getPassword from client : " + loginDto.getPassword());


       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                                         loginDto.getUsername(), loginDto.getPassword()
                                                        )); /* this will take you to CustomUserDetailsServiceImpl loadUserByUsername( since we implemented UserDetailsService which is predefined interface which contains loadUserByUsername and
                                                                                             we override loadUserByUsername in our  CustomUserDetailsServiceImpl CLASS)
                                                            */
//        now authentication have info of user
        System.out.println("Data Came from DB in authentication getUserName : " + authentication.getName()); // username
        System.out.println("Data Came from DB in authentication isAuthenticated : " + authentication.isAuthenticated()); //true if authenticated
        System.out.println("Data Came from DB in authentication obj getAuthorities ROLES: " + authentication.getAuthorities()); // from here will get set of roles :ex- [ROLE_USER]
        System.out.println("Data Came  from DB authentication Details : " + authentication.getPrincipal()); //org.springframework.security.core.userdetails.User [Username=noorul, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]]
//        System.out.println("Data Came from DB in authentication obj : " + authentication.toString());
//    in  authentication what we get:  UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=noorul, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]], Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[ROLE_USER]]

        SecurityContextHolder.getContext()
                            .setAuthentication(authentication);

        String token = jwtTokenProvider.generateJwtToken(authentication);

    return token;
//    return String.format("User of username : %s logged-in successfully! ", loginDto.getUsername());
    }

}
