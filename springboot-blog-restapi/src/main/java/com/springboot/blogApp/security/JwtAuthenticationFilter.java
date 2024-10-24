package com.springboot.blogApp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter { //JwtAuthenticationFilter CLASS will use to authenticate the jwt token.
//     so this will execute before the Security FILTER .

//    now let's inject the JwtTokenProvider - Utility class and use its methods
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {


// 1st :  will get JWT token from client.
        String token = getTokenFromRequest(request);
        System.out.println("****** 1st thing run in JWT authentication. In JwtAuthenticationFilter : doFilterInternal()  token got from header: " + token);

//   validate the Token : we can use out utility method from JwtTokenProvider class : this will be false for login since for login we only need username and pw.
        if(StringUtils.hasText(token) && jwtTokenProvider.validateJwtToken(token)) { // StringUtils.hasText(token) will check non-empty nonNull

//   get username from Token
                String username = jwtTokenProvider.getUsernameFromJwtToken(token); // will get username from token
            System.out.println("getting userName from JWT token: " + username);

//  load the username from DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); // will get userDetails from DB
            System.out.println("User details in authenticationFilter from DB through loadUserByUsername:  " + userDetails.toString());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // since it already contains in userDetails
                    userDetails.getAuthorities() // ROLE
            );

//  nest we need to add HttpServletRequest request object to authenticationToken
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

// now let's set the authenticationToken to the Security Context holder
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
         } // if END

// now lets call the do FilterChain CLASS method doFilter and pass the request and response
        filterChain.doFilter(request, response); // this line is crucial for the proper functioning of the filter chain in Spring Security.
    }

//   we need to extract the JWT token from the Authorization header of the HttpServletRequest.
    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization"); // getting from POSTMAN - "Authorization" in header
//        since there will be two things : <Bearer> <jwtToken> - so here will extract only token

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){  // StringUtils.hasText is a utility method provided by Spring that checks whether a string contains actual text. More precisely, it returns true if the string is not null, not empty, and contains at least one non-whitespace character. This adds a layer of robustness to your code by ensuring that the Authorization header is not just present but also contains meaningful text.
            return bearerToken.substring(7); // will get after <Bearer > 7 character
        }

        return null;
    }
}

// TODO : JwtAuthenticationFilter extends OncePerRequestFilter:  will this run very 1st when w hit any end point of rest API ?
/*
    Yes, JwtAuthenticationFilter extending OncePerRequestFilter typically runs as part of the filter chain in a Spring application. When you hit any endpoint of the REST API,
    the request passes through this filter if it's configured correctly. The **OncePerRequestFilter** ensures that the filter's doFilterInternal method is executed once per request,
    making it suitable for tasks like JWT authentication, where you want to intercept and process requests before they reach your controllers. So, to answer your question: yes,
    it will run as part of the request processing flow for every request hitting your REST API endpoints.

 */

// TODO : About the filterChain : line - filterChain.doFilter(request, response); -- will explore later more
/*
-> The line filterChain.doFilter(request, response); in your JwtAuthenticationFilter class is crucial for the proper functioning of the filter chain in Spring Security.

Explanation:

1-> Purpose of filterChain.doFilter(request, response);:

                  1)  This line invokes the next filter in the chain (if any) or the resource at the end of the chain (like a servlet or endpoint handler method).
                  2)  In a servlet container, filters are arranged in a chain. When a request comes in, it passes through each filter sequentially before reaching
                            the servlet or endpoint handler. After processing in each filter, filterChain.doFilter(request, response); moves the request/response pair
                             to the next filter in the chain.
                  3) If this line is not called within a filter's doFilterInternal method, the request will not proceed further down the chain. This could result in
                             the request not reaching its intended endpoint or not being properly handled by subsequent security filters.

2->       Execution Flow:

                  1)  When a request enters the doFilterInternal method of JwtAuthenticationFilter, it first **extracts the JWT token** from the request headers using
                                getTokenFromRequest(request).

                  2) Then, it **validates** the JWT token using jwtTokenProvider.validateJwtToken(token). If valid, it retrieves the username from the
                                token using jwtTokenProvider.getUsernameFromJwtToken(token).

                  3)  The filter then loads the UserDetails from the database using userDetailsService.loadUserByUsername(username).

                  4)  It creates an Authentication object (UsernamePasswordAuthenticationToken) containing the UserDetails and sets it to the SecurityContextHolder.

                  5)  Finally, filterChain.doFilter(request, response); ensures that the request/response pair continues down the filter chain to eventually
                                reach the servlet or endpoint handler that serves the requested resource.


Importance:
        Ensuring Proper Flow: Without filterChain.doFilter(request, response);, the request processing stops at your filter. No subsequent filters or servlets/endpoints would be able to process the request further, effectively halting the request processing chain prematurely.

        Correct Order of Filters: Spring Security filters are arranged in a specific order to handle various aspects such as authentication, authorization, and session management. filterChain.doFilter(request, response); ensures that the request flows through all necessary filters in the correct order to enforce security policies and handle user authentication/authorization properly.

In summary, filterChain.doFilter(request, response); is essential for maintaining the flow of request processing in Spring Security. It ensures that after performing authentication and setting up security context, the request continues to be processed by subsequent filters or reaches the endpoint handler to provide the requested resource.


 */
