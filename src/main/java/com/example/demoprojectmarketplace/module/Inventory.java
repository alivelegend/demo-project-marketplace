package com.example.demoprojectmarketplace.module;

import com.example.demoprojectmarketplace.module.security.User;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "inventories")
@Getter
@Setter
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
