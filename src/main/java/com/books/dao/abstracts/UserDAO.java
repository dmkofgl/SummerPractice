package com.books.dao.abstracts;

import com.books.entities.User;

public interface UserDAO {
    User takeUser(String login, String password);

    boolean checkExistsUser(String login);
}
