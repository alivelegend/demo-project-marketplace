package com.example.demoprojectmarketplace.service;

import com.example.demoprojectmarketplace.dto.request.InventoryDtoRequest;
import com.example.demoprojectmarketplace.module.Inventory;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface InventoryService {

    List<Inventory> getByUserId(Long id);

    Optional<Inventory> getById(Long id);

    Inventory getByIdThrowException(Long id);

    Optional<Inventory> getByItemId(Long id);
    Inventory getByItemIdThrowException(Long id);

    Inventory create(InventoryDtoRequest dtoRequest , Principal principal );

    void delete(Long id);

}
