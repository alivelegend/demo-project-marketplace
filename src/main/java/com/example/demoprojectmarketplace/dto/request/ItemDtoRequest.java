package com.example.demoprojectmarketplace.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDtoRequest {

    private String name;

    private String description;

    private Long uniqueNumber;

}
