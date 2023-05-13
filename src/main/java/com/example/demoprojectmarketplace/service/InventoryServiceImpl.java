package com.example.demoprojectmarketplace.service;

import com.example.demoprojectmarketplace.dto.request.InventoryDtoRequest;
import com.example.demoprojectmarketplace.exception.CustomExceptionMessage;
import com.example.demoprojectmarketplace.exception.custom.NotFoundException;
import com.example.demoprojectmarketplace.exception.custom.RepositoryCreateException;
import com.example.demoprojectmarketplace.exception.custom.RepositoryDeleteException;
import com.example.demoprojectmarketplace.module.Inventory;
import com.example.demoprojectmarketplace.module.Item;
import com.example.demoprojectmarketplace.module.security.User;
import com.example.demoprojectmarketplace.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class InventoryServiceImpl implements InventoryService{


    private final InventoryRepository inventoryRepository;

    private final ItemService itemService;

    private final UserService userService;

    @Override
    public List<Inventory> getByUserId(Long id) {
        return inventoryRepository.findByUserId(id);
    }

    @Override
    public Optional<Inventory> getById(Long id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public Inventory getByIdThrowException(Long id) {
        return this.getById(id).orElseThrow(() -> new NotFoundException(CustomExceptionMessage.NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public Optional<Inventory> getByItemId(Long id) {
        return inventoryRepository.findByItemId(id);
    }

    @Override
    public Inventory getByItemIdThrowException(Long id) {
        return this.getByItemId(id).orElseThrow(() -> new RuntimeException(CustomExceptionMessage.NOT_FOUND_EXCEPTION_MESSAGE));
    }

    private Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }



    @Override //?
    public Inventory create(InventoryDtoRequest dtoRequest , Principal principal ) {
        Inventory inventory = new Inventory();

        try {

            String username = principal.getName();

            Item item = itemService.getByItemIdThrowException(dtoRequest.getItemId());
            User user = userService.getByUsernameThrowsException(username);



            inventory.setItem(item);
            inventory.setUser(user);


            return this.save(inventory);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RepositoryCreateException(CustomExceptionMessage.CREATE_EXCEPTION_MESSAGE);
        }
    }


    @Override
    public void delete(Long id) {

        Inventory inventory = this.getByItemIdThrowException(id);

        try {
            inventoryRepository.delete(inventory);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RepositoryDeleteException(CustomExceptionMessage.DELETE_EXCEPTION_MESSAGE);
        }

    }
}
