package com.springboot.blogApp.respository;

import com.springboot.blogApp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 77. Creating JPA Repository - CommentRepository
// we are extending it so that we can have JpaRepository methods which will help to communicate with DB.
public interface CommentRepository extends JpaRepository<Comment, Long> {

//    Here we'll declare some custom query methods so that it can be used in --layer: (Need: since there is no such method provided, so we write and in this camel case SpringBoot will figure it out )
//    we want to get list of comments of a particular post-id:
    List<Comment> findByPostId(Long postId);//findByPostId it's a custom query method and not a predefined.
}
