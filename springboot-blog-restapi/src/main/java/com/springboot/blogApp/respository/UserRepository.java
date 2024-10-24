package com.springboot.blogApp.respository;

import com.springboot.blogApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Crud operation methods are provided by JpaRepository

    Optional<User> findByEmail(String email); // Optional: since we can have user or not, so both possibilities are handled. It'll be easy to handle if we return Optional
    Optional<User> findByUsername(String username);;
    Boolean existsByEmail(String email); // standard way to check existence
    Boolean existsByUsername(String username);

}
