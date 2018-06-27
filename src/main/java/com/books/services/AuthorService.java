package com.books.services;

import com.books.entities.Person;
import com.books.services.abstracts.AuthorServiceable;
import com.books.storage.abstracts.AuthorDAO;
import com.books.storage.concrete.SQL.AuthorSQLDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AuthorService implements AuthorServiceable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorService.class);
    public static final AuthorService INSTANCE = new AuthorService();

    private AuthorDAO storage;

    private AuthorService() {
        storage = AuthorSQLDAO.getInstance();
    }

    public static AuthorService getInstance() {
        return INSTANCE;
    }

    @Override
    public void addAuthor(Person person) {
        storage.add(person);
    }

    @Override
    public void setPerson(int id, Person person) {
        storage.saveItem(id, person);
    }

    @Override
    public List<Person> getAllAuthors() {
        return storage.getList();
    }

    @Override
    public Person getAuthorById(int id) {
        Person result = null;
        result = storage.getAuthorById(id);
        return result;
    }


}
