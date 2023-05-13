package com.example.demoprojectmarketplace.repository;

import com.example.demoprojectmarketplace.module.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByUserId(Long id);

    Optional<Inventory> findByItemId(Long id);


}
