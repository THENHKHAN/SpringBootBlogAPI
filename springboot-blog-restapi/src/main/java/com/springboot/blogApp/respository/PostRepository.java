package com.springboot.blogApp.respository;


import com.springboot.blogApp.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

// 54. Creating JPA Repository - PostRepository : Repository Or DAO layer which will communicate with DB (By using JpaRepository methods)
//There is no need to annotate here with Repository since it extend JpaRepository (which already annotated by Repository)
public interface PostRepository extends JpaRepository<Post, Long> {

}
