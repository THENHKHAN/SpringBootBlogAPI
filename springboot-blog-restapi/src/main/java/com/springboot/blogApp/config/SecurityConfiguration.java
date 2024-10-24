package com.springboot.blogApp.config;

import com.springboot.blogApp.security.JwtAuthenticationEntryPoint;
import com.springboot.blogApp.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity // by enabling this we can apply preFilter/ptrAuthorize , postAuthorize etc for making access route based on role. So we to do some new annotation on Post update notation
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService; // UserDetailsService spring boot provide this class so we don't have to define explicitly.

//password encode
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    Lect-121. Configuring JWT in Spring Security Configuration
                    // let's 1st inject the JwtAuthenticationEntryPoint
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // before this JwtAuthenticationFilter will execute to authenticate the JWT token.
        System.out.println("CLASS:SecurityConfiguration.java-  In securityFilterChain() for filtering user");
        // Let's configure securityFilterChain to enable basic HTTP authentication:
        configureAuthorization(http); // whatever was here i moved in this method

// next we will create a Jwt authentication filter so that jwt execute before spring security filter.

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // so that's means authenticationFilter will execute before the spring security filter.
        return http.build();
    }

    private void configureAuthorization(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll(); // This means all users can access GET requests if the endpoint has the prefix /api/
                    authorize.requestMatchers("/api/auth/**").permitAll();
                    authorize.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**").permitAll()
                            .anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults()) // lets define role based authentication : Admin, user
                .exceptionHandling(exception->
                                   exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(session->
                                   session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );


    //Other way:    .authorizeHttpRequests( (authorize  -> authorize
//                                                                                            .anyRequest()
//                                                                                            .authenticated()))
//                                                                                            .httpBasic(Customizer.withDefaults()
//                                                                   );
    }


/*      AuthenticationFilter extends OncePerRequestFilter       see this .class file here it will be doFilterInternal func  and inside this attemptAuthentication(this will get the username n pw from convert method inside it.) see how it's attempting the request.
   110. How Database Authentication Works in Spring Security

 */

    @Bean
    public AuthenticationManager authorizationManager (AuthenticationConfiguration configuration ) throws Exception { // this will talk to userDetailsService and passwordEncoder to authenticate the user details
        System.out.println("CLASS: SecurityConfiguration.java inside authorizationManager()");
        return configuration.getAuthenticationManager() ;
    }


    /*
    Commented below bcz we wanted to do based on DB and not hard coded.
     */
//        @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails ramesh = User.builder()
//                .username("noor")
//                .password(passwordEncoder().encode("noor"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(ramesh, admin);
//    }

}


/*
 .authorizeHttpRequests(auth -> {
                    System.out.println("auth variable here means => " + auth.toString()); //rg.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer$AuthorizationManagerRequestMatcherRegistry@10f7918f
//                *******IMP   ABOUT Method Based and RequestMatcher Ways Applying
//                    By using this request matcher and role - we don't need to use hasRole in controller through @PreAuthorize notation. Other with Notation way- can be used then we can comment the auth.requestMatchers and roles.
//                  if we are using @PreAuthorize annotation then that called applying sb security on METHOD LEVEL.
                    auth.requestMatchers("api/security/normal**")
                                    .hasRole("NORMAL");
//                                    .hasAnyRole("NORMAL", "ADMIN"); // how to define multiple role to an endpoint

                    auth.requestMatchers("api/security/admin**")
                                    .hasRole("ADMIN");

                    auth.requestMatchers("api/security/public-user").permitAll();
                    auth.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults());
 */