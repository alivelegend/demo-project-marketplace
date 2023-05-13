package com.example.demoprojectmarketplace.mapper;

import com.example.demoprojectmarketplace.dto.response.MarketplaceDtoResponse;
import com.example.demoprojectmarketplace.module.Marketplace;


//?
public class MarketplaceMapper {

    public static MarketplaceDtoResponse marketplaceToDto(Marketplace marketplace) {
        MarketplaceDtoResponse marketplaceDtoResponse = new MarketplaceDtoResponse();

        marketplaceDtoResponse.setId(UserMapper.userToDto(marketplace.getUser()).getId());//
        marketplaceDtoResponse.setItem(ItemMapper.itemToDto(marketplace.getItem()));
        marketplaceDtoResponse.setPrice(marketplace.getPrice());
        marketplaceDtoResponse.setDescription(marketplace.getDescription());

        return marketplaceDtoResponse;
    }
}
