package com.example.demoprojectmarketplace.service;

import com.example.demoprojectmarketplace.dto.request.MarketplaceCreateDtoRequest;
import com.example.demoprojectmarketplace.dto.request.MarketplaceUpdateDtoRequest;
import com.example.demoprojectmarketplace.exception.CustomExceptionMessage;
import com.example.demoprojectmarketplace.exception.custom.NotFoundException;
import com.example.demoprojectmarketplace.exception.custom.RepositoryCreateException;
import com.example.demoprojectmarketplace.exception.custom.RepositoryDeleteException;
import com.example.demoprojectmarketplace.exception.custom.RepositoryUpdateException;
import com.example.demoprojectmarketplace.module.Item;
import com.example.demoprojectmarketplace.module.Marketplace;
import com.example.demoprojectmarketplace.module.security.User;
import com.example.demoprojectmarketplace.repository.MarketplaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MarketplaceServiceImpl implements MarketplaceService {

    private final MarketplaceRepository marketplaceRepository;

    private final ItemService itemService;

    private final UserService userService;

    @Override
    public Optional<Marketplace> getById(Long id) {
        return marketplaceRepository.findById(id);
    }

    @Override
    public Marketplace getByIdThrowException(Long id) {
        return this.getById(id).orElseThrow(() -> new NotFoundException(CustomExceptionMessage.NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public List<Marketplace> getAll() {
        return marketplaceRepository.findAll();
    }

    private Marketplace save(Marketplace marketplace) {
        return marketplaceRepository.save(marketplace);
    }


    @Override
    public Marketplace create(MarketplaceCreateDtoRequest dtoRequest) {
        Marketplace marketplace = new Marketplace();

        try {

            User user = userService.getByUserIdThrowException(dtoRequest.getUserId());

            marketplace.setName(dtoRequest.getName());
            marketplace.setUser(user);
            marketplace.setPrice(dtoRequest.getPrice());
            marketplace.setDescription(dtoRequest.getDescription());


            Item item = itemService.getByItemIdThrowException(dtoRequest.getItemId());
            marketplace.setItem(item);

            return this.save(marketplace);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new RepositoryCreateException(CustomExceptionMessage.CREATE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public Marketplace update(MarketplaceUpdateDtoRequest dtoRequest, Long id) {
        Marketplace marketplace = this.getByIdThrowException(id);

        try {

            marketplace.setPrice(dtoRequest.getPrice());
            marketplace.setDescription(dtoRequest.getDescription());

            return this.save(marketplace);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new RepositoryUpdateException(CustomExceptionMessage.UPDATE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void delete(Long id) {
        Marketplace marketplace = this.getByIdThrowException(id);

        try {
            marketplaceRepository.delete(marketplace);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new RepositoryDeleteException(CustomExceptionMessage.DELETE_EXCEPTION_MESSAGE);
        }

    }
}
