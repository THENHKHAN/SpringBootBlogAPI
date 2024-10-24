package com.springboot.blogApp.service;

import com.springboot.blogApp.payload.CommentDto;

import java.util.List;

public interface CommentService {
//     will do all the function declaration here so that we can implement in serviceImpClass
    CommentDto createComment(Long postId, CommentDto commentDto);
//    Here we have to declare the method that we want to implement amd use the provided custom method in repo through repo object
    List<CommentDto> getCommentsByPostId (Long postId);

    CommentDto getCommentById(Long postId, Long commentId);// will GET comment by comment id with a desired postId

    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDtoRequest) ; // UPDATE comment by comment id if it belongs to  post id with commentId=postId

    String deleteComment(Long postId, Long commentId); // DELETE comment by comment id if it belongs to  post id with commentId=postId
}
