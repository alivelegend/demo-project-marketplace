package com.example.demoprojectmarketplace.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDtoRequest {

    private Long itemId;

    private Long sellerId;

    private Long buyerId;

}
