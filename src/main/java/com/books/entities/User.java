package com.books.entities;

public class User {
    private int id;

    public String getLogin() {
        return login;
    }

    private String login;

    public User(int id, String login) {
        this.id = id;
        this.login = login;
    }
}
