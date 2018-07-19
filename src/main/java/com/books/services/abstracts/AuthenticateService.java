package com.books.services.abstracts;

import com.books.entities.User;

public interface AuthenticateService {
    boolean checkExistsUser(String login);

    User takeUser(String login, String password);
}
