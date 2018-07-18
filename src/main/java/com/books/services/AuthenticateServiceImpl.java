package com.books.services;

import com.books.dao.abstracts.UserDAO;
import com.books.entities.User;
import com.books.services.abstracts.AuthenticateService;


public class AuthenticateServiceImpl implements AuthenticateService {
    private UserDAO userDAO;

    private AuthenticateServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean checkExistsUser(String login) {
        return userDAO.checkExistsUser(login);
    }

    @Override
    public User takeUser(String login, String password) {
        return userDAO.takeUser(login, password);
    }
}
