package com.example.demoprojectmarketplace.service;

import com.example.demoprojectmarketplace.dto.request.ItemDtoRequest;
import com.example.demoprojectmarketplace.module.Item;

import java.util.Optional;

public interface ItemService {

    Optional<Item> getById(Long id);

    Item getByItemIdThrowException(Long id);

    Item create(ItemDtoRequest dtoRequest);

    Item update(ItemDtoRequest dtoRequest, Long id);

    void delete(Long id);
}
