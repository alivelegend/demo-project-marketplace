package com.example.demoprojectmarketplace.service;

import com.example.demoprojectmarketplace.dto.request.ItemDtoRequest;
import com.example.demoprojectmarketplace.exception.CustomExceptionMessage;
import com.example.demoprojectmarketplace.exception.custom.NotFoundException;
import com.example.demoprojectmarketplace.exception.custom.RepositoryCreateException;
import com.example.demoprojectmarketplace.exception.custom.RepositoryDeleteException;
import com.example.demoprojectmarketplace.exception.custom.RepositoryUpdateException;
import com.example.demoprojectmarketplace.module.Item;
import com.example.demoprojectmarketplace.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    private Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Optional<Item> getById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public Item getByItemIdThrowException(Long id) {
        return this.getById(id).orElseThrow(() -> new NotFoundException(CustomExceptionMessage.NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public Item create(ItemDtoRequest dtoRequest) {
        Item item = new Item();

        try {

            item.setName(dtoRequest.getName());
            item.setDescription(dtoRequest.getDescription());
            item.setUniqueNumber(dtoRequest.getUniqueNumber());

            return this.save(item);

        } catch (Exception e){
            log.error(e.getMessage());
            throw new RepositoryCreateException(CustomExceptionMessage.CREATE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public Item update(ItemDtoRequest dtoRequest, Long id) {
        Item item = new Item();

        try {

            item.setName(dtoRequest.getName());
            item.setDescription(dtoRequest.getDescription());

            return this.save(item);

        } catch (Exception e){
            log.error(e.getMessage());
            throw new RepositoryUpdateException(CustomExceptionMessage.UPDATE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void delete(Long id) {
        Item item = this.getByItemIdThrowException(id);

        try {
            itemRepository.delete(item);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RepositoryDeleteException(CustomExceptionMessage.DELETE_EXCEPTION_MESSAGE);
        }
    }
}
