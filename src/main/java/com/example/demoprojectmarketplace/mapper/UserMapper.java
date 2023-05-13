package com.example.demoprojectmarketplace.mapper;

import com.example.demoprojectmarketplace.dto.response.UserDtoResponse;
import com.example.demoprojectmarketplace.module.security.User;

//??
public class UserMapper {

    public static UserDtoResponse userToDto(User user) {
        UserDtoResponse userDtoResponse = new UserDtoResponse();

        userDtoResponse.setId(user.getId());
        userDtoResponse.setName(user.getName());
        userDtoResponse.setUsername(user.getUsername());

        return userDtoResponse;
    }
}
