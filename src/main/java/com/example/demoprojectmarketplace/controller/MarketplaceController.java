package com.example.demoprojectmarketplace.controller;

import com.example.demoprojectmarketplace.dto.request.MarketplaceCreateDtoRequest;
import com.example.demoprojectmarketplace.dto.request.MarketplaceUpdateDtoRequest;
import com.example.demoprojectmarketplace.dto.response.MarketplaceDtoResponse;
import com.example.demoprojectmarketplace.exception.custom.ExceptionHandling;
import com.example.demoprojectmarketplace.mapper.MarketplaceMapper;
import com.example.demoprojectmarketplace.module.Marketplace;
import com.example.demoprojectmarketplace.service.MarketplaceService;
import com.example.demoprojectmarketplace.service.facade.CancelFacadeService;
import com.example.demoprojectmarketplace.service.facade.PostFacadeService;
import com.example.demoprojectmarketplace.service.facade.PurchaseFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/marketplace")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_MARKETPLACE_ADMIN') OR hasRole('ROLE_TOTAL_ADMIN')")
public class MarketplaceController extends ExceptionHandling {

    private final MarketplaceService marketplaceService;

    private final PurchaseFacadeService purchaseFacadeService;

    @Autowired
    private final PostFacadeService postFacadeService;

    private final CancelFacadeService cancelFacadeService;


    @Autowired
    private final TransactionController transactionController;

    @GetMapping("/")
    public ResponseEntity<List<MarketplaceDtoResponse>> getAll() {
        List<Marketplace> marketplaceList = marketplaceService.getAll();

        List<MarketplaceDtoResponse> marketplaceDtoResponseList = marketplaceList.stream()
                .map(MarketplaceMapper::marketplaceToDto).collect(Collectors.toList());

        return new ResponseEntity<>(marketplaceDtoResponseList, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<MarketplaceDtoResponse> post(Principal principal,@RequestBody MarketplaceCreateDtoRequest dtoRequest) {

        postFacadeService.postItem(principal,dtoRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @PostMapping("/purchase/{id}")
    public ResponseEntity<HttpStatus> purchase(@PathVariable(name = "id") Long id, Principal principal) {

       purchaseFacadeService.makePurchase(id, principal);

       return new ResponseEntity<>(HttpStatus.OK);

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<MarketplaceDtoResponse> update(@RequestBody MarketplaceUpdateDtoRequest dtoRequest,
                                                         @PathVariable(name = "id") Long id) {
        Marketplace marketplace = marketplaceService.update(dtoRequest, id);

        MarketplaceDtoResponse marketplaceDtoResponse = MarketplaceMapper.marketplaceToDto(marketplace);

        return new ResponseEntity<>(marketplaceDtoResponse, HttpStatus.OK);
    }


    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<MarketplaceDtoResponse> cancel(@PathVariable(name = "id") Long id, Principal principal) {

        cancelFacadeService.cancelSale(id, principal);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
