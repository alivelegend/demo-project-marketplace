package com.example.demoprojectmarketplace.module.security;

import com.example.demoprojectmarketplace.module.Inventory;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",nullable = false,unique = true)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "balance")
    private Double balance;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;


}
