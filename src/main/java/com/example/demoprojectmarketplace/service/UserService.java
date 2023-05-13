package com.example.demoprojectmarketplace.service;

import com.example.demoprojectmarketplace.dto.request.UserAuthorizationDtoRequest;
import com.example.demoprojectmarketplace.dto.request.UserFillBalanceDtoRequest;
import com.example.demoprojectmarketplace.dto.request.UserRegistrationDtoRequest;
import com.example.demoprojectmarketplace.dto.response.UserBalanceDtoResponse;
import com.example.demoprojectmarketplace.dto.response.UserDtoResponse;
import com.example.demoprojectmarketplace.module.security.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

public interface UserService {

    Optional<User> getByUsername(String username);

    Optional<User> getById(Long id);

    User getByUsernameThrowsException(String username);

    User getByUserIdThrowException(Long id);

    void registration(UserRegistrationDtoRequest dtoRequest);

    ResponseEntity<UserDtoResponse> authorization(UserAuthorizationDtoRequest dtoRequest, HttpServletRequest request);

    User fillBalance(UserFillBalanceDtoRequest dtoRequest,Principal principal);



}
