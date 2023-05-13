package com.example.demoprojectmarketplace.repository;

import com.example.demoprojectmarketplace.module.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByBuyerId(Long id);

    Transaction findBySellerId(Long id);
}
