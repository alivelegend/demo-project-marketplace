package com.example.demoprojectmarketplace.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFillBalanceDtoRequest {

    private Long userId;

    private Double balance;
}
