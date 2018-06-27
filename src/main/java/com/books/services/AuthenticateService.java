package com.books.services;

import com.books.entities.User;
import com.books.services.abstracts.AuthenticateServiceable;
import com.books.storage.abstracts.UserDAO;
import com.books.storage.concrete.SQL.UserSQLDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticateService implements AuthenticateServiceable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateService.class);
    private static final AuthenticateService INSTANCE = new AuthenticateService();

    private UserDAO storage;

    public static AuthenticateService getInstance() {
        return INSTANCE;
    }

    private AuthenticateService() {
        storage = UserSQLDAO.getInstance();
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
