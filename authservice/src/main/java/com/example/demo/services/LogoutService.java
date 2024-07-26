package com.example.demo.services;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenService tokenService;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String header= request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        if (header== null|| !header.startsWith("Bearer ")){
            return;
        }
        jwt = header.substring(7);
        var tokenStored =tokenService.getToken(jwt)
                .orElse(null);
        if (tokenStored!=null){
            tokenStored.setRevoked(true);
            tokenStored.setExpired(true);
            tokenService.saveToken(tokenStored);
            SecurityContextHolder.clearContext();
        }

    }
}
