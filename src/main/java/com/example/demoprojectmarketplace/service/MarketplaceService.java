package com.example.demoprojectmarketplace.service;

import com.example.demoprojectmarketplace.dto.request.MarketplaceCreateDtoRequest;
import com.example.demoprojectmarketplace.dto.request.MarketplaceUpdateDtoRequest;
import com.example.demoprojectmarketplace.module.Marketplace;

import java.util.List;
import java.util.Optional;

public interface MarketplaceService {

    Optional<Marketplace> getById(Long id);

    Marketplace getByIdThrowException(Long id);

    List<Marketplace> getAll();

    Marketplace create(MarketplaceCreateDtoRequest dtoRequest);

    Marketplace update(MarketplaceUpdateDtoRequest dtoRequest, Long id);

    void delete(Long id);

}
