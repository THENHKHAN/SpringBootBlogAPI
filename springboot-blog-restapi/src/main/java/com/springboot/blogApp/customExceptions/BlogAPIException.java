package com.springboot.blogApp.customExceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// this will be used whenever we write some business logic Or validate request parameter.
@Getter
public class BlogAPIException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public BlogAPIException(HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }

}
