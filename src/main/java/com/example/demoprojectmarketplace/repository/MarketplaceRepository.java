package com.example.demoprojectmarketplace.repository;

import com.example.demoprojectmarketplace.module.Marketplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketplaceRepository extends JpaRepository<Marketplace, Long> {
}
