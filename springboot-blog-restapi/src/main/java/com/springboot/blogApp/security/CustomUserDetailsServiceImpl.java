package com.springboot.blogApp.security;

// Here, will implement a custom user detail service to by implementing a UserDetailsService interface from Spring Security provided.
// https://www.tutorialspoint.com/spring_security/spring_security_form_login_with_database.htm#:~:text=Here%20is%20this%20class%20can,used%20to%20authenticate%20the%20user.
// https://www.baeldung.com/spring-security-authentication-with-a-database
// https://medium.com/@barbieri.santiago/implementing-user-authentication-in-java-apis-using-spring-boot-spring-security-and-spring-data-cb9eac2361f6

import com.springboot.blogApp.models.User;
import com.springboot.blogApp.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// 109. UserDetailsService Interface Implementation - CustomUserDetailsService
// this class will load the user object from DB, and it will make the DB authentication. And now will have to go to config package-> SecurityConfiguration Class to load the user detail from this class and make the authentication.
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {    // This service is crucial for authenticating users against the database.
// *** this works 1st then its info goes to security filter ***

    @Autowired
    private UserRepository userRepository; // dependency injection.

//    public CustomUserDetailsServiceImpl(UserRepository userRepository) { // DON'T need to use this since AUTOWIRED
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // it returns userDetails
        System.out.println("loadUserByUsername() , username from client :  " + username);
        System.out.println("*************** BEFORE loading User from DB, in loadUserByUsername() ************");

        // Fetching user details from the repository
        Optional<User> userDets = userRepository.findByUsername(username);

        // Handle the case where the user is not found in globally as bad credential if user does not exist.
//        if (userDets.isPresent()){  NO need for this
//            System.out.println("User with username: " + username + " not found in DB!");
//        }

        User user = userDets.get();
        System.out.println("Username Query From DB: " + user.getUsername());

        // Returning UserDetails object for Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet())
        );

    }











//        User user = userRepository.findByUsername(username)
//                 .orElseThrow(()->
//                         new UsernameNotFoundException("User name not found in Database. " + username));
//
//        System.out.println("*************** AFTER loading User ************");
//        System.out.println("Username : " + user.getUsername());
//        System.out.println("UserMail : " + user.getEmail());
//        System.exit(1);
//        Set<GrantedAuthority> authorities = user.getRoles()
//                .stream()
//                .map( (role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet()); // we have converted a set of role to set of granted authority.
//
//        System.out.println(" Set<GrantedAuthority> authorities ---> " + Arrays.toString(authorities.toArray())); // Set<GrantedAuthority> authorities ---> [ROLE_USER] FOR ADMIN - Set<GrantedAuthority> authorities ---> [ROLE_ADMIN]
//        System.out.println("Returned stuff from Service CustomUserDetailsServiceImpl class ---> " + new org.springframework.security.core.userdetails
//                .User(user.getUsername(), user.getPassword(),authorities));// Returned stuff from Service CustomUserDetailsServiceImpl class ---> org.springframework.security.core.userdetails.User [Username=noorul, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_USER]]
//// iF ADMIN - Returned stuff from Service CustomUserDetailsServiceImpl class ---> org.springframework.security.core.userdetails.User [Username=admin, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_ADMIN]]
//
//        /* now lets convert this object to spring security provider object */
//
//        return new org.springframework.security.core.userdetails
//                .User(
//                        user.getUsername(),
//                        user.getPassword(),
//                        authorities
//                    );
//
//    }

}
