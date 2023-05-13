package com.example.demoprojectmarketplace.service;

import com.example.demoprojectmarketplace.dto.request.UserAuthorizationDtoRequest;
import com.example.demoprojectmarketplace.dto.request.UserFillBalanceDtoRequest;
import com.example.demoprojectmarketplace.dto.request.UserRegistrationDtoRequest;
import com.example.demoprojectmarketplace.dto.response.UserBalanceDtoResponse;
import com.example.demoprojectmarketplace.dto.response.UserDtoResponse;
import com.example.demoprojectmarketplace.exception.CustomExceptionMessage;
import com.example.demoprojectmarketplace.exception.custom.*;
import com.example.demoprojectmarketplace.mapper.UserMapper;
import com.example.demoprojectmarketplace.module.Inventory;
import com.example.demoprojectmarketplace.module.security.Role;
import com.example.demoprojectmarketplace.module.security.User;
import com.example.demoprojectmarketplace.repository.UserRepository;
import com.example.demoprojectmarketplace.security.JWTTokenProvider;
import com.example.demoprojectmarketplace.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;
//Не доделал
@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    @Lazy
    private final AuthenticationManager authenticationManager;

    private final JWTTokenProvider jwtTokenProvider;

    private final RoleService roleService;

    private final String AUTHENTICATION_EXCEPTION = "Логин или пароль неправильно указаны";

    private final String USERNAME_ALREADY_EXIST = "Данный логин уже занят";


    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getByUsernameThrowsException(String username) {
        return this.getByUsername(username).orElseThrow(()-> new NotFoundException(CustomExceptionMessage.NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public User getByUserIdThrowException(Long id) {
        return this.getById(id).orElseThrow(() -> new NotFoundException(CustomExceptionMessage.NOT_FOUND_EXCEPTION_MESSAGE));
    }

    private User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void registration(UserRegistrationDtoRequest dtoRequest) {
        String username = dtoRequest.getUsername().toLowerCase().trim();
        String password = dtoRequest.getPassword().trim();

        Double currentBalance = 0.0;

        Optional<User> user = this.getByUsername(username);

        if (user.isPresent()) throw new AlreadyExistException(USERNAME_ALREADY_EXIST);

        try {
            User createdUser = new User();

            createdUser.setUsername(username);
            createdUser.setPassword(encoder.encode(password));
            createdUser.setName(dtoRequest.getName());
            createdUser.setBalance(currentBalance);

            Inventory inventory = new Inventory();
            createdUser.setInventory(inventory);

            Role role = this.roleService.getByTitle("ROLE_USER");
            createdUser.setRole(role);


            this.save(createdUser);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RepositoryCreateException(CustomExceptionMessage.CREATE_EXCEPTION_MESSAGE);
        }

    }

    @Override
    public ResponseEntity<UserDtoResponse> authorization(UserAuthorizationDtoRequest dtoRequest, HttpServletRequest request) {
        String username = dtoRequest.getUsername().toLowerCase().trim();
        String password = dtoRequest.getPassword().trim();

        try {
            this.authenticate(username,password);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomShowMessageException(this.AUTHENTICATION_EXCEPTION);
        }


        try {
            User user = this.getByUsernameThrowsException(username);
            UserPrincipal userPrincipal = new UserPrincipal(user);

            String IP = jwtTokenProvider.getIP(request);

            HttpHeaders httpHeaders = this.JWTHeader(userPrincipal,IP);

            return new ResponseEntity<>(UserMapper.userToDto(user), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnexpectedException(CustomExceptionMessage.UNEXPECTED_MESSAGE);
        }
    }

    @Override
    public User fillBalance(UserFillBalanceDtoRequest dtoRequest, Principal principal) {
        try {

            String username = principal.getName();
            User user = this.getByUsernameThrowsException(username);

            Double currentBalance = user.getBalance();

            Double updatedBalance = currentBalance + dtoRequest.getBalance();

            user.setBalance(updatedBalance);

            return this.save(user);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new CustomShowMessageException("Ошибка при пополнении баланса");
        }
    }


    private HttpHeaders JWTHeader(UserPrincipal userPrincipal, String IP) {
        HttpHeaders httpHeaders = new HttpHeaders();

        String JWT = jwtTokenProvider.generateToken(userPrincipal, IP);

        httpHeaders.add(HttpHeaders.AUTHORIZATION, JWT);

        return httpHeaders;

    }


    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.getByUsernameThrowsException(username);

        return new UserPrincipal(user);
    }
}
