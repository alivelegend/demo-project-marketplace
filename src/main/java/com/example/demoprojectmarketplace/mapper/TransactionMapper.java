package com.example.demoprojectmarketplace.mapper;

import com.example.demoprojectmarketplace.dto.response.TransactionDtoResponse;
import com.example.demoprojectmarketplace.module.Transaction;

public class TransactionMapper {//Переделать

    public static TransactionDtoResponse transactionToDto(Transaction transaction) {
        TransactionDtoResponse transactionDtoResponse = new TransactionDtoResponse();

        transactionDtoResponse.setSellerId(UserMapper.userToDto(transaction.getSeller()).getId());
        transactionDtoResponse.setItemId(ItemMapper.itemToDto(transaction.getItem()).getId());
        transactionDtoResponse.setBuyerId(UserMapper.userToDto(transaction.getBuyer()).getId());

        return transactionDtoResponse;
    }
}
