package com.example.demoprojectmarketplace.repository;

import com.example.demoprojectmarketplace.module.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByTitle(String title);
}
