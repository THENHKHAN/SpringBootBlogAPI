package com.springboot.blogApp.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

//56. Creating DTO Class - PostDto : It is a Design pattern that can be followed: It is used to transfer/response/request data. For Big Project we use this DESIGN PATTERN.
// we can pass Dto Object to client-Server as well as Server-Client. Here what we do is we convert Jpa entity(Models/table) to Dto object then return as response and not directly the jpa Entity.
//IMP: We already have notes from the course to read more.
/*
Data Transfer Object Design Pattern is a frequently used design pattern. It is basically used to pass data with multiple
attributes in one shot from client to server vice-versa, to avoid multiple calls to a remote server.

Example: If the entity class contains sensitive information
(ex: password, codes, photo etc.) then if we return the entity
directly then the client will get this sensitive information which
causes security issues.
 */
@Data
public class PostDto {
    private  Long id;
    @NotEmpty //Post title should not be null or empty
    @Size (min = 2, message = "Post title should have at least 2 characters")
    private  String title;

    @NotEmpty //Post description should have not be null or empty
    @Size (min = 5, message = "Post Description should have at least 5 characters" )
    private  String description;

    @NotEmpty // Post content should have not be null or empty
    @Size (min = 5, message = "Post content should have at least 5 characters" )
    private  String content;
    private Set<CommentDto> comments; //(this variable name must be same as Post entity attribute); // it will help to retrieve the all the comment set belong to this id

}
