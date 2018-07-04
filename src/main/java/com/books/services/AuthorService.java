package com.books.services;

import com.books.entities.Person;
import com.books.services.abstracts.AuthorServiceable;
import com.books.dao.abstracts.AuthorDAO;
import com.books.dao.concrete.SQL.AuthorSQLDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

public class AuthorService implements AuthorServiceable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorService.class);

    private AuthorDAO storage;

    private AuthorService(AuthorDAO authorDAO) {
        storage = authorDAO;
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
