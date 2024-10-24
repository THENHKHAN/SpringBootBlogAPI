package com.springboot.blogApp.customExceptions;

import com.springboot.blogApp.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // this annotation is used to Handle the Exception Globally.
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    handle specific exception : // this is used to handle specific exception and sending the custom response to client.
    @ExceptionHandler(ResourceNotFoundException.class) // we mention here the class of which type of Exception we want to send.
// will define methods like a restapi controller methods since we want to send back to client as Http response.
    public ResponseEntity<ErrorDetails> handleResourseNotFoundException (ResourceNotFoundException exception, // ResourceNotFoundException: type of exception we want send back
                                                                         WebRequest webRequest){ // since we wanted to send back http response to client
    ErrorDetails errorDetails = new ErrorDetails(
                                                ZonedDateTime.now(),
                                                exception.getMessage(), //error msg
                                                webRequest.getDescription(false), // false: since we want only useful info and not whole trace will only send the url where it occurred in the details key .
                                                HttpStatus.NOT_FOUND.value()
                                                );
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

//    now lets write for 2nd BlogAPIException
    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIException (BlogAPIException exception, // BlogAPIException: type of exception we want send back
                                                                         WebRequest webRequest){ // since we wanted to send back http response to client
        ErrorDetails errorDetails = new ErrorDetails(
                                                    ZonedDateTime.now(),
                                                    exception.getMessage(), //error msg
                                                    webRequest.getDescription(false), // false: since we want only useful info and not whole trace will only send the url where it occurred in the details key .
                                                    HttpStatus.BAD_REQUEST.value()
                                                     );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // lets define VALIDATION customize response : 96. Customizing Validation Response
//@ExceptionHandler(MethodArgumentNotValidException.class)
//public ResponseEntity<Object> handleMethodArgumentNotValidException (MethodArgumentNotValidException exception, // BlogAPIException: type of exception we want send back
//                                                                                WebRequest webRequest){ // since we wanted to send back http response to client
//    Map<String, String> errors = new HashMap<>();
//    exception.getBindingResult().getAllErrors().forEach( (error) -> {
//        String fieldName = ((FieldError)error).getField(); // will get the field name where validation is failed
//        String message   = error.getDefaultMessage();
//        errors.put(fieldName, message);
//    });
//
//    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//}      //  OR BY OVERRIDING FROM ResponseEntityExceptionHandler CLASS : There was some error in the above approach that's y went with below one
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( (error) -> {
            String fieldName = ((FieldError) error).getField(); // will get the field name where validation is failed
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException (AccessDeniedException exception, // BlogAPIException: type of exception we want send back
                                                                WebRequest webRequest){ // since we wanted to send back http response to client
        ErrorDetails errorDetails = new ErrorDetails(
                ZonedDateTime.now(),
                exception.getMessage(), //error msg
                webRequest.getDescription(false), // false: since we want only useful info and not whole trace will only send the url where it occurred in the details key .
                HttpStatus.UNAUTHORIZED.value()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }


//if any of the above did not get exception and still got exception then they will be handling in this global exceptionHandler.
//    global exception : like someone sends id instead of 23 the sends or "23", "afaf" which is Kind of misMatchArgs etc...
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleExceptionGlobally(Exception exception,
                                                                WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                                                    ZonedDateTime.now(),
                                                    exception.getMessage(), //error msg
                                                    webRequest.getDescription(false), // false: since we want only useful info and not whole trace will only send the url where it occurred in the details key .
                                                    HttpStatus.BAD_REQUEST.value()
                                                     );
        return  new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
