package com.springboot.blogApp.customExceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 55. Creating Custom Exception - ResourceNotFoundException : Will throw when a post(Resource) not exist of specified ID

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends  RuntimeException{

    private String resourceName;
    private String fieldName;
    private Long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue){
        super(String.format("%s not found with %s : %d", resourceName, fieldName, fieldValue)); //Ex:  Post not found with id : 1
        this.resourceName  = resourceName;
        this.fieldName     = fieldName;
        this.fieldValue    = fieldValue;
//        will show in postman(Or return Response) console like this :  "message": "Post not found with id : 1" ===> if id 1 is not present in DB , Post is the resource or Entity.
    }

}
