package com.example.demoprojectmarketplace.service.facade;

import com.example.demoprojectmarketplace.dto.request.InventoryDtoRequest;
import com.example.demoprojectmarketplace.dto.request.TransactionDtoRequest;
import com.example.demoprojectmarketplace.exception.CustomExceptionMessage;
import com.example.demoprojectmarketplace.exception.custom.PurchaseItemException;
import com.example.demoprojectmarketplace.module.Marketplace;
import com.example.demoprojectmarketplace.module.security.User;
import com.example.demoprojectmarketplace.service.InventoryService;
import com.example.demoprojectmarketplace.service.MarketplaceService;
import com.example.demoprojectmarketplace.service.TransactionService;
import com.example.demoprojectmarketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Log4j2
public class PurchaseFacadeServiceImpl implements PurchaseFacadeService {

    private final MarketplaceService marketplaceService;

    private final InventoryService inventoryService;

    private final UserService userService;

    private final TransactionService transactionService;

    @Override
    @Transactional//?
    public void makePurchase(Long id, Principal principal) {

        Marketplace marketplace = marketplaceService.getByIdThrowException(id);

        marketplaceService.delete(id);

        InventoryDtoRequest inventoryDtoRequest = new InventoryDtoRequest();

        String username = principal.getName();

        User user = userService.getByUsernameThrowsException(username);

        if (marketplace.getUser().getId().equals(user.getId())) {

            throw new PurchaseItemException(CustomExceptionMessage.EQUAL_ID_PURCHASE_MESSAGE);
        }

        if (user.getBalance() < marketplace.getPrice()){

            throw new PurchaseItemException(CustomExceptionMessage.NOT_ENOUGH_FUNDS);
        }

        user.setBalance(user.getBalance() - marketplace.getPrice());

        inventoryDtoRequest.setItemId(marketplace.getItem().getId());
        inventoryDtoRequest.setUserId(user.getId());

        inventoryService.create(inventoryDtoRequest,principal);

        TransactionDtoRequest transactionDtoRequest = new TransactionDtoRequest();

        transactionDtoRequest.setItemId(marketplace.getItem().getId());
        transactionDtoRequest.setSellerId(marketplace.getUser().getId());
        transactionDtoRequest.setBuyerId(user.getId());

        transactionService.create(transactionDtoRequest);

    }
}
