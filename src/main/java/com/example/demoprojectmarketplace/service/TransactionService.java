package com.example.demoprojectmarketplace.service;

import com.example.demoprojectmarketplace.dto.request.TransactionDtoRequest;
import com.example.demoprojectmarketplace.module.Transaction;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface TransactionService {

    List<Transaction> getAll();

    Transaction create(TransactionDtoRequest dtoRequest);

}
