package com.example.demoprojectmarketplace.repository;

import com.example.demoprojectmarketplace.module.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


}
