package com.example.demoprojectmarketplace.service.facade;

import com.example.demoprojectmarketplace.dto.request.MarketplaceCreateDtoRequest;
import com.example.demoprojectmarketplace.exception.CustomExceptionMessage;
import com.example.demoprojectmarketplace.exception.custom.PostItemException;
import com.example.demoprojectmarketplace.mapper.InventoryMapper;
import com.example.demoprojectmarketplace.module.Inventory;
import com.example.demoprojectmarketplace.module.security.User;
import com.example.demoprojectmarketplace.service.InventoryService;
import com.example.demoprojectmarketplace.service.MarketplaceService;
import com.example.demoprojectmarketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostFacadeServiceImpl implements PostFacadeService{

    private final InventoryService inventoryService;

    private final UserService userService;

    private final MarketplaceService marketplaceService;

    @Override
    @Transactional
    public void postItem( Principal principal,@RequestBody MarketplaceCreateDtoRequest dtoRequest) {
//        Inventory inventory = inventoryService.getByIdThrowException(dtoRequest.getItemId());

        Inventory inventory = inventoryService.getByItemIdThrowException(dtoRequest.getItemId());

        try {
            inventoryService.delete(inventory.getItem().getId());//Данный метод перестал удалять запись в таблице inventory(dtoRequest.getItemId()

            MarketplaceCreateDtoRequest marketplaceCreateDtoRequest = new MarketplaceCreateDtoRequest();

            String username = principal.getName();

            User user = userService.getByUsernameThrowsException(username);

            marketplaceCreateDtoRequest.setName(inventory.getItem().getName());
            marketplaceCreateDtoRequest.setUserId(user.getId());
            marketplaceCreateDtoRequest.setItemId(dtoRequest.getItemId());
            marketplaceCreateDtoRequest.setPrice(dtoRequest.getPrice());
            marketplaceCreateDtoRequest.setDescription(dtoRequest.getDescription());

            marketplaceService.create(marketplaceCreateDtoRequest);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new PostItemException(CustomExceptionMessage.POST_ITEM_EXCEPTION_MESSAGE);
        }

    }
}
