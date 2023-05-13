package com.example.demoprojectmarketplace.controller;

import com.example.demoprojectmarketplace.dto.request.TransactionDtoRequest;
import com.example.demoprojectmarketplace.dto.response.TransactionDtoResponse;
import com.example.demoprojectmarketplace.exception.custom.ExceptionHandling;
import com.example.demoprojectmarketplace.mapper.TransactionMapper;
import com.example.demoprojectmarketplace.module.Transaction;
import com.example.demoprojectmarketplace.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_TRANSACTION_ADMIN') OR hasRole('ROLE_TOTAL_ADMIN')")
@Component
public class TransactionController extends ExceptionHandling {

    private final TransactionService transactionService;

    //Нужен ли @GetMapping для просмотра истории транзакций для клиента

    @GetMapping("/")
    public ResponseEntity<List<TransactionDtoResponse>> getAll() {
        List<Transaction> transactionList = transactionService.getAll();

        List<TransactionDtoResponse> transactionDtoResponseList = transactionList.stream()
                .map(TransactionMapper :: transactionToDto).collect(Collectors.toList());

        return new ResponseEntity<>(transactionDtoResponseList, HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<TransactionDtoResponse> create(@RequestBody TransactionDtoRequest dtoRequest) {
        Transaction transaction = transactionService.create(dtoRequest);

        TransactionDtoResponse transactionDtoResponse = TransactionMapper.transactionToDto(transaction);

        return new ResponseEntity<>(transactionDtoResponse, HttpStatus.CREATED);
    }


}
