package com.example.demoprojectmarketplace.dto.request;

import lombok.Getter;

@Getter
public class UserRegistrationDtoRequest {

//    @NotBlank
    private String username;

    private String password;

    private String name;


}
