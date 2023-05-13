package com.example.demoprojectmarketplace.service.facade;

import java.security.Principal;

public interface PurchaseFacadeService {

    void makePurchase(Long id, Principal principal);//delete markeplace, create inv,create transaction
}
