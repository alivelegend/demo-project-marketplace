package com.example.demoprojectmarketplace.mapper;

import com.example.demoprojectmarketplace.dto.response.ItemDtoResponse;
import com.example.demoprojectmarketplace.module.Item;

public class ItemMapper {

    public static ItemDtoResponse itemToDto(Item item) {
        ItemDtoResponse itemDtoResponse = new ItemDtoResponse();

        itemDtoResponse.setId(item.getId());
        itemDtoResponse.setName(item.getName());
        itemDtoResponse.setDescription(item.getDescription());

        return itemDtoResponse;
    }
}
