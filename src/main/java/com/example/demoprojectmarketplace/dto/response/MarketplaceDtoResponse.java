package com.example.demoprojectmarketplace.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketplaceDtoResponse {

    private Long id;

    private ItemDtoResponse item;

    private Double price;

    private String description;

    private Long userId;

    private String name;

}
