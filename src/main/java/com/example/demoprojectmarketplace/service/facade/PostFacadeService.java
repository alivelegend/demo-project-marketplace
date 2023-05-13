package com.example.demoprojectmarketplace.service.facade;

import com.example.demoprojectmarketplace.dto.request.MarketplaceCreateDtoRequest;

import java.security.Principal;

public interface PostFacadeService {

    void postItem( Principal principal, MarketplaceCreateDtoRequest dtoRequest);//delete inventory, create marketplace

}
