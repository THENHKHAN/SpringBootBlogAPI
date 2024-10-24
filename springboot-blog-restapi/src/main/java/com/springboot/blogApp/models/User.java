package com.springboot.blogApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

//    let's make the mapping m-n
@ManyToMany(fetch = FetchType.EAGER, // means when we load the user entity it will load along with as well
            cascade = CascadeType.ALL)  // to apply with its child as well
@JoinTable(
        name = "user_role", //the third table as user_role which will contain user id and role id as FK.
        joinColumns = @JoinColumn (name = "user_id", referencedColumnName = "id"),  // this id is same as User table and will be stored as user_id(as FK) in the third table(user_roles table)
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") // of role table
)
    private Set<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", username='" + username + '\'' +
                '}';
    }
}
