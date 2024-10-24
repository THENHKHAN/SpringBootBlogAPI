package com.springboot.blogApp.controller;


import com.springboot.blogApp.payload.CommentDto;
import com.springboot.blogApp.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Comments Controller", description = "CRUD REST APIs for Comment Resource")
@RequestMapping("/api")
public class CommentController {

    @Autowired
    CommentService commentService ;

//    create Comment on desired posts(identity by post id)
//    Create Blog Post Comment rest api
// POST   http://localhost:8080/api/posts/{postId}/comments === http://localhost:8080/api/posts/1/comments
    @Operation(summary = "REST API to Create Comments", description = "Do comment on a particular post")
    @PostMapping("/posts/{postId}/comments")
    @PreAuthorize("hasRole('ADMIN')") // so this route only accessible by ADMIN only
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,
                                                    @PathVariable(value = "postId") Long postId){

        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }


//    get Comment on desired posts(identity by post id)
//    Get Blog Comments rest api
//  GET  http://localhost:8080/api/posts/{postId}/comments === http://localhost:8080/api/posts/1/comments
    @Operation(summary = "REST API to Get Comments", description = "Get all comments of a post by post-id")
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable (value = "postId") Long postId){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId)); // get list of all comments of a provided postid
    }


//    get Comment based on comment id with a particular posts id.
//    Get Blog Comments rest api
// GET   http://localhost:8080/api/posts/{postId}/comments/{id}  === localhost:8080/api/posts/1/comments/2
    @Operation(summary = "REST API to Get Comment", description = "Get single comments of a post by post-id and comment-id")
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") Long commentId){
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    //    UPDATE Comment based on comment id with a particular posts id. : // update comment by comment id if it belongs to  post id with commentId=postId
//    PUT rest api
// PUT   http://localhost:8080/api/posts/{postId}/comments/{id}  === localhost:8080/api/posts/1/comments/2
    @Operation(summary = "REST API to Update Comment", description = "Get updated comments of a post by post-id and comment-id")
    @PutMapping("/posts/{postId}/comments/{id}")
    @PreAuthorize("hasRole('ADMIN')") // so this route only accessible by ADMIN only
    public ResponseEntity<CommentDto> updateComment(@Valid @PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
    }

    //    DELETE Comment based on comment id with a particular posts id. : // DELETE comment by comment id if it belongs to  post id with commentId=postId
//    DELETE rest api
// DELETE   http://localhost:8080/api/posts/{postId}/comments/{id}  === localhost:8080/api/posts/1/comments/2
    @Operation(summary = "REST API to Delete Comment", description = "Delete the comment by post-id and comment-id")
    @DeleteMapping("/posts/{postId}/comments/{id}")
    @PreAuthorize("hasRole('ADMIN')") // so this route only accessible by ADMIN only
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long commentId){
        return ResponseEntity.ok(commentService.deleteComment(postId, commentId));
    }

}
