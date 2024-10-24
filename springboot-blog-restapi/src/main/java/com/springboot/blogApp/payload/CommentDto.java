package com.springboot.blogApp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class CommentDto {
    private Long id;

    @NotEmpty
    @Size(min = 4, message = "Name must have at least 4 characters")
    private  String name;

    @NotEmpty
    @Email(message = "In Email, Must be a valid email address")
    private  String email;

    @NotEmpty
    @Size(min = 5, message = "Comment body must have at least 5 characters")
    private  String body;
}
