package com.example.demoprojectmarketplace.controller;

import com.example.demoprojectmarketplace.dto.request.UserAuthorizationDtoRequest;
import com.example.demoprojectmarketplace.dto.request.UserFillBalanceDtoRequest;
import com.example.demoprojectmarketplace.dto.request.UserRegistrationDtoRequest;
import com.example.demoprojectmarketplace.dto.response.UserBalanceDtoResponse;
import com.example.demoprojectmarketplace.dto.response.UserDtoResponse;
import com.example.demoprojectmarketplace.exception.custom.ExceptionHandling;
import com.example.demoprojectmarketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController extends ExceptionHandling {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<UserDtoResponse> registration(@RequestBody UserRegistrationDtoRequest dtoRequest) {
        userService.registration(dtoRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/authorization")
    public ResponseEntity<UserDtoResponse> authorization(@RequestBody UserAuthorizationDtoRequest dtoRequest,
                                                         HttpServletRequest request) {
        return userService.authorization(dtoRequest, request);
    }

    @PostMapping("/fill")
    public ResponseEntity<UserBalanceDtoResponse> fillBalance(Principal principal
            , @RequestBody UserFillBalanceDtoRequest dtoRequest) {
        userService.fillBalance(dtoRequest, principal);

        return new ResponseEntity<>(HttpStatus.OK);
    }






}
