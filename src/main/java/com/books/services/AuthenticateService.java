package com.books.services;

import com.books.entities.User;
import com.books.storage.abstracts.UserDAO;
import com.books.storage.concrete.SQL.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticateService {
    UserDAO repository;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticateService.class);

    private static final AuthenticateService INSTANCE = new AuthenticateService();

    public static AuthenticateService getInstance() {
        return INSTANCE;
    }

    private AuthenticateService() {
        repository = UserRepository.getInstance();
    }

    public User takeUser(String login, String password) {
        return repository.takeUser(login, password);
    }
}
