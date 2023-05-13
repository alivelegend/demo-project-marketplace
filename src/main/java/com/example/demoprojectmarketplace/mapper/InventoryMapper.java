package com.example.demoprojectmarketplace.mapper;

import com.example.demoprojectmarketplace.dto.response.InventoryDtoResponse;
import com.example.demoprojectmarketplace.module.Inventory;

//?
public class InventoryMapper {

    public static InventoryDtoResponse inventoryToDto(Inventory inventory) {
        InventoryDtoResponse inventoryDtoResponse = new InventoryDtoResponse();

        inventoryDtoResponse.setItem(ItemMapper.itemToDto(inventory.getItem()));
        inventoryDtoResponse.setUser(UserMapper.userToDto(inventory.getUser()));

//        inventoryDtoResponse.setItemId(inventory.getItem().getId());
//        inventoryDtoResponse.setUserId(inventory.getUser().getId());

        return inventoryDtoResponse;

    }
}
