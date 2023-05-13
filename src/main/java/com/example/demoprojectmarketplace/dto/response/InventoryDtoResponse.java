package com.example.demoprojectmarketplace.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryDtoResponse {

    private ItemDtoResponse item;

    private UserDtoResponse user;
}
