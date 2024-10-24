package com.springboot.blogApp.respository;

import com.springboot.blogApp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role, Long> {
    Role findByName(String roleName);
}
