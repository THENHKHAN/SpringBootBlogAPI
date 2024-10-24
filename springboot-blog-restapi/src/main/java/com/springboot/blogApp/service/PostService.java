package com.springboot.blogApp.service;

import com.springboot.blogApp.payload.PostDto;
import com.springboot.blogApp.payload.PostResponse;

import java.util.List;

public interface PostService {
    //     will do all the function declaration here so that we can implement in serviceImpClass
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    String deletePost(Long id);
    PostResponse getAllPostByPagination(int pageNo, int pageSize, String postsSortBy, String sortDir);

}
