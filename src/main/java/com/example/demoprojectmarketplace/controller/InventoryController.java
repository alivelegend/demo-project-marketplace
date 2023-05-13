package com.example.demoprojectmarketplace.controller;

import com.example.demoprojectmarketplace.dto.request.InventoryDtoRequest;
import com.example.demoprojectmarketplace.dto.response.InventoryDtoResponse;
import com.example.demoprojectmarketplace.exception.custom.ExceptionHandling;
import com.example.demoprojectmarketplace.mapper.InventoryMapper;
import com.example.demoprojectmarketplace.module.Inventory;
import com.example.demoprojectmarketplace.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_INVENTORY_ADMIN') OR hasRole('ROLE_TOTAL_ADMIN')")
public class InventoryController extends ExceptionHandling {

    private final InventoryService inventoryService;

    @GetMapping("/user-id/{id}")
    public ResponseEntity<List<InventoryDtoResponse>> getById(@PathVariable (name = "id") Long id) {
        List<Inventory> inventoryList = inventoryService.getByUserId(id);

        List<InventoryDtoResponse> inventoryDtoResponseList = inventoryList.stream().map(InventoryMapper :: inventoryToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(inventoryDtoResponseList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<InventoryDtoResponse> create(Principal principal, @RequestBody InventoryDtoRequest dtoRequest) {
        Inventory inventory = inventoryService.create(dtoRequest,principal);

        InventoryDtoResponse inventoryDtoResponse = InventoryMapper.inventoryToDto(inventory);

        return new ResponseEntity<>(inventoryDtoResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable (name = "id") Long id) {
        inventoryService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
