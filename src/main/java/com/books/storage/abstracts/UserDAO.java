package com.books.storage.abstracts;

import com.books.entities.User;

public interface UserDAO {
    public User takeUser(String login, String password);

}
