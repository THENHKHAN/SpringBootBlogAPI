package com.springboot.blogApp.models;


import com.springboot.blogApp.payload.CommentDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


// 53. Creating JPA Entity - Post Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (
        name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "title", nullable = false)
    private  String title ;

    @Column(name = "description", nullable = false)
    private  String description ;

    @Column(name = "content", nullable = false)
    private  String content ;

//    relation with comment entity

    @OneToMany(mappedBy = "post", // remember there will be no extra column added in this Parent Post entity
               cascade = CascadeType.ALL // done so that when we make chnages parent entity will reflect in child as well(Here Comment is the child)
               )// post- this has to be same as variable in Comment (i.e. private Post post;)
    private Set<Comment> comments; // will be getting list of comments of a particular post id
//    other you can use is Set for uniqueness (But somehow it was not working so may be in future will see if was using like this Set<Comment> comments = new HashSet<>())
}
