package com.example.demoprojectmarketplace.service.facade;

import java.security.Principal;

public interface CancelFacadeService {

    void cancelSale(Long id, Principal principal);
}
