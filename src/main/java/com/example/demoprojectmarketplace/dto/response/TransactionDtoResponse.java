package com.example.demoprojectmarketplace.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDtoResponse {

    private Long itemId;

    private Long sellerId;

    private Long buyerId;

}
