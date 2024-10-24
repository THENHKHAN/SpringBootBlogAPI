package com.springboot.blogApp.controller;


import com.springboot.blogApp.payload.PostDto;
import com.springboot.blogApp.payload.PostResponse;
import com.springboot.blogApp.service.PostService;
import com.springboot.blogApp.utils.AppConstants;
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
@Tag(name = "Post Controller", description = "CRUD REST APIs for Post Resource")
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

//    public PostController(PostService postService) { Since used above @Autowired so don't need to write Constructor
//        this.postService = postService;
//    }

//    Create Blog Post rest api
//    http://localhost:8080/api/posts
    @Operation(summary = "REST API to Create post", description = "Returns Posts description")
    @PostMapping("") // Or    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // so this route only acccessible by ADMIN only
    public ResponseEntity<PostDto> createPost( @Valid @RequestBody PostDto postDto){
        return  new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }


//    Get all records:  Blog GET rest api
// http://localhost:8080/api/posts
    @Operation(summary = "REST API to Get all the posts", description = "Returns all the posts")
    @GetMapping("")
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return  new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    //    Get record by desired id or particular record based on ID:  Blog GET rest api
// http://localhost:8080/api/posts/{id} === http://localhost:8080/api/posts/1
    @Operation(summary = "REST API to Get a post by id", description = "Returns a Post as per the post id")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable (name = "id") Long postId){
        return  ResponseEntity.ok( postService.getPostById(postId) ); // OR
//        return  ResponseEntity.ok().body( postService.getPostById(postId) );
    }


    //    UPDATE Record based on Post id :  Blog PUT rest api
// http://localhost:8080/api/posts/{id}
    @Operation(summary = "REST API to  Update post by id", description = "Returns the updated post")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // so this route only accessible by ADMIN only
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable (name = "id") Long postId){
        return ResponseEntity.ok( postService.updatePost(postDto, postId) );
    }

    //    DELETE Post/Record based on Post id :  Blog DELETE rest api
// http://localhost:8080/api/posts/{id}

    @Operation(summary = "REST API to Delete post by id", description = "Returns Successful or not ")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // so this route only accessible by ADMIN only
    public ResponseEntity<String> deletePost(@PathVariable (name = "id") Long postId){
        return ResponseEntity.ok(postService.deletePost(postId));
    }


//    70. Pagination Support for Get All Posts REST API
//http://localhost:8080/api/posts/pageNo=0&pageSize=10&sortBy=title&sortDir=asc         // pageSize means : no of rec in table will come at once
    @Operation(summary = "REST API to Get post with pagination ", description = "Returns response based on provided pageNo, pageSize, Sort or not")                                          // pageNo : 0 means 1st 10 rec , 1 means next 2 rec (if we have total 12 rec)
    @GetMapping("/pagination/") // we could modify the above getAllPost But here doing extra mapping so that i can go and check explicitly
    public ResponseEntity<PostResponse> getAllPostByPagination(
        @RequestParam (name = "pageNo",   defaultValue = AppConstants.DEFAULT_PAGE_NUM, required = false) int pageNo,
        @RequestParam (name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
        @RequestParam (name = "sortBy",   defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String postsSortBy,
        @RequestParam (name = "sortDir",  defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){

        return new ResponseEntity<>(postService.getAllPostByPagination(pageNo, pageSize, postsSortBy, sortDir), HttpStatus.OK);
    }

}
