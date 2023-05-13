package com.example.demoprojectmarketplace.service;

import com.example.demoprojectmarketplace.module.security.Role;

import java.util.Optional;

public interface RoleService {

    Role getByTitle(String title);
}
