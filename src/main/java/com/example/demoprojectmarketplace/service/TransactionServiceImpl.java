package com.example.demoprojectmarketplace.service;

import com.example.demoprojectmarketplace.dto.request.TransactionDtoRequest;
import com.example.demoprojectmarketplace.exception.CustomExceptionMessage;
import com.example.demoprojectmarketplace.exception.custom.RepositoryCreateException;
import com.example.demoprojectmarketplace.module.Item;
import com.example.demoprojectmarketplace.module.Transaction;
import com.example.demoprojectmarketplace.module.security.User;
import com.example.demoprojectmarketplace.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final ItemService itemService;

    private final UserService userService;

    private Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }


    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction create(TransactionDtoRequest dtoRequest) {
        Transaction transaction = new Transaction();

        try {

            User seller = userService.getByUserIdThrowException(dtoRequest.getSellerId());
            Item item = itemService.getByItemIdThrowException(dtoRequest.getItemId());
            User buyer = userService.getByUserIdThrowException(dtoRequest.getBuyerId());


            transaction.setSeller(seller);
            transaction.setItem(item);
            transaction.setBuyer(buyer);


            return this.save(transaction);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RepositoryCreateException(CustomExceptionMessage.CREATE_EXCEPTION_MESSAGE);
        }
    }
}
