package com.springboot.blogApp.security;

import com.springboot.blogApp.customExceptions.BlogAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {  // In this class will get the jwt properties from application.properties file

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-millisecond}")
    private long jwtTokenExpirationTime;

//    let's generate the JWT Token and return the token
    public String generateJwtToken(Authentication authentication){
        System.out.println(" ................ In Process of Generating JWT token in generateJwtToken().................. " );
        String username = authentication.getName(); // from here will get the username

        long currentTimeMillis = System.currentTimeMillis();
        Date expirationDate = new Date(currentTimeMillis + jwtTokenExpirationTime);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key()) // will create a separate methode to get the key : bcs will be using at multiple places to get the key
                .compact(); // club all the information and generate the token

        return token;
    }

//    getting key
    private Key key(){ // from - import java.security.Key;
       return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)) ; // from - import io.jsonwebtoken.security.Keys;
    }

//    get username from JWT token
    public String getUsernameFromJwtToken(String token){
       Claims claims =  Jwts.parserBuilder()
                            .setSigningKey(key())
                            .build() // it will build the jwtParser
                            .parseClaimsJws(token)
                            .getBody();
       return  claims.getSubject(); // it will return the username from Token
    }

//    this will validate the JWT token
    public boolean validateJwtToken(String token){
            try {
                Jwts.parserBuilder()
                        .setSigningKey(key())
                        .build()
                        .parse(token); // It will parse the JWT token and if it will find any issue then throw the exception.

            }
//            let's handle all the possible exceptions
            catch (MalformedJwtException malformedJwtException){
                throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
            }
            catch (ExpiredJwtException expiredJwtException){
                throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT Token ");
            }
            catch (UnsupportedJwtException unsupportedJwtException){
                throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
            }
            catch (IllegalArgumentException illegalArgumentException){
                throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Illegal argument: Jwt claims is null or empty ");
            }
        return true;
    }
}
