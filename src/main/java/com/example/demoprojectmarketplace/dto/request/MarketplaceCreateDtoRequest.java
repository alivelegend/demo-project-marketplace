package com.example.demoprojectmarketplace.dto.request;

import com.example.demoprojectmarketplace.dto.response.UserDtoResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketplaceCreateDtoRequest {

    private String name;

    private Double price;

    private String description;

    private Long itemId;

    private Long userId;


}
