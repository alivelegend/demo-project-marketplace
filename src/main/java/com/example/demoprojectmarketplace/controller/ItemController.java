package com.example.demoprojectmarketplace.controller;

import com.example.demoprojectmarketplace.dto.request.ItemDtoRequest;
import com.example.demoprojectmarketplace.dto.response.ItemDtoResponse;
import com.example.demoprojectmarketplace.exception.custom.ExceptionHandling;
import com.example.demoprojectmarketplace.mapper.ItemMapper;
import com.example.demoprojectmarketplace.module.Item;
import com.example.demoprojectmarketplace.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ITEM_ADMIN', 'ROLE_TOTAL_ADMIN', 'ROLE_USER')")
public class ItemController extends ExceptionHandling {

    private final ItemService itemService;

    @PostMapping("/create")
    public ResponseEntity<ItemDtoResponse> create(@RequestBody ItemDtoRequest dtoRequest) {
        Item item = itemService.create(dtoRequest);

        ItemDtoResponse itemDtoResponse = ItemMapper.itemToDto(item);

        return new ResponseEntity<>(itemDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ItemDtoResponse> update(@RequestBody ItemDtoRequest dtoRequest,
                                                  @PathVariable (name = "id") Long id) {
        Item item = itemService.update(dtoRequest, id);

        ItemDtoResponse itemDtoResponse = ItemMapper.itemToDto(item);

        return new ResponseEntity<>(itemDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> delete(Long id) {
        itemService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
