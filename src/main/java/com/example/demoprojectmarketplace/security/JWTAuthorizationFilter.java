package com.example.demoprojectmarketplace.security;

import com.example.demoprojectmarketplace.constatnt.WebConstant;
import com.example.demoprojectmarketplace.module.security.User;
import com.example.demoprojectmarketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JWTTokenProvider jwtTokenProvider;

    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

        if (Arrays.stream(WebConstant.PERMIT_ALL_URL).anyMatch(v-> v.equals(request.getRequestURI()))) {
            filterChain.doFilter(request, response);
            return;
        }

        String headerAuthorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (headerAuthorization == null || !headerAuthorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String JWT = headerAuthorization.substring("Bearer ".length());

            String username = jwtTokenProvider.getSubject(JWT);

            jwtTokenProvider.isTokenValid(JWT, request);

            User user = userService.getByUsernameThrowsException(username);
            UserPrincipal userPrincipal = new UserPrincipal(user);

            Authentication authentication = jwtTokenProvider.getAuthentication(userPrincipal.getUsername()
                    , new HashSet<>(userPrincipal.getAuthorities()),request);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (Exception e){
            SecurityContextHolder.clearContext();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        filterChain.doFilter(request, response);


    }
}
