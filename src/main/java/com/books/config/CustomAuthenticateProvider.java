package com.books.config;

import com.books.entities.User;
import com.books.services.abstracts.AuthenticateService;
import com.books.services.abstracts.EncryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticateProvider implements AuthenticationProvider {
    @Autowired
    private AuthenticateService authenticateService;
    @Autowired
    private EncryptService encryptService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        password = encryptService.md5Encrypt(password);


        if (authenticateService.checkExistsUser(name)) {
            User user = authenticateService.takeUser(name, password);
            if (user == null) {
                return null;
            }
            // array contains grants
            return new UsernamePasswordAuthenticationToken(
                    name, password, new ArrayList<>());
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}