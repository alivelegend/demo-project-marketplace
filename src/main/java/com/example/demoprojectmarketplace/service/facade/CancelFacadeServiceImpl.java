package com.example.demoprojectmarketplace.service.facade;

import com.example.demoprojectmarketplace.dto.request.InventoryDtoRequest;
import com.example.demoprojectmarketplace.module.Marketplace;
import com.example.demoprojectmarketplace.module.security.User;
import com.example.demoprojectmarketplace.service.InventoryService;
import com.example.demoprojectmarketplace.service.MarketplaceService;
import com.example.demoprojectmarketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Log4j2
public class CancelFacadeServiceImpl implements CancelFacadeService {

    private final MarketplaceService marketplaceService;

    private final UserService userService;

    private final InventoryService inventoryService;

    @Override
    @Transactional
    public void cancelSale(Long id, Principal principal) {

        Marketplace marketplace = marketplaceService.getByIdThrowException(id);

        marketplaceService.delete(id);

        InventoryDtoRequest inventoryDtoRequest = new InventoryDtoRequest();

        String username = principal.getName();

        User user = userService.getByUsernameThrowsException(username);

        inventoryDtoRequest.setUserId(user.getId());
        inventoryDtoRequest.setItemId(marketplace.getItem().getId());

        inventoryService.create(inventoryDtoRequest,principal);

    }
}
