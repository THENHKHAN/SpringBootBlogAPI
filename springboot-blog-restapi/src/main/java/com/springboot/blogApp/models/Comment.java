package com.springboot.blogApp.models;

//76. Creating JPA Entity - Comment : Comment Entity

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private  String name;
    private  String email;
    private  String body;

//    for making relation between two entity:
//since many comments can be done in one post (hint to rem : read left to right Many to One - comments to Post). Means One post can have many comments
    @ManyToOne(fetch = FetchType.LAZY) // so that it works when relation is made
    @JoinColumn(name = "post_id", nullable = false)//can't be null // this is going to be the FK in this table (as post_id)
    private Post post ; // now will be doing some changes in Post entity Since we need to make BiDIRECTIONAL (From post - ONE2Many)
}
