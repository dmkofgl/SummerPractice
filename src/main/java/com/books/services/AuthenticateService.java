package com.books.services;

import com.books.dao.abstracts.UserDAO;
import com.books.entities.User;
import com.books.services.abstracts.AuthenticateServiceable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class AuthenticateService implements AuthenticateServiceable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateService.class);

    private UserDAO storage;


    private AuthenticateService(UserDAO userDAO) {
        storage = userDAO;
    }

    @Override
    public boolean checkExistsUser(String login) {
        return storage.checkExistsUser(login);
    }

    @Override
    public User takeUser(String login, String password) {
        return storage.takeUser(login, password);
    }
}
