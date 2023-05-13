package com.example.demoprojectmarketplace.service;

import com.example.demoprojectmarketplace.module.security.Role;
import com.example.demoprojectmarketplace.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public Role getByTitle(String title) {
        return roleRepository.findByTitle(title);
    }
}
