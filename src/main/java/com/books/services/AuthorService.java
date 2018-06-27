package com.books.services;

import com.books.entities.Person;
import com.books.storage.abstracts.AuthorDAO;
import com.books.storage.concrete.SQL.AuthorSQLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

public class AuthorService implements com.books.services.abstracts.AuthorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorService.class);
    public static final AuthorService INSTANCE = new AuthorService();

    private AuthorDAO repository;

    private AuthorService() {
        repository = AuthorSQLRepository.getInstance();
    }

    public static AuthorService getInstance() {
        return INSTANCE;
    }


    public void addAuthor(Person person) {
        repository.add(person);
    }

    public void setPerson(int id, Person person) {
        repository.setItem(id, person);
    }


    public List<Person> getAllAuthors() {
        return repository.getList();
    }

    public Person getAuthorById(int id) {
        Person result = null;
        result = repository.getAuthorById(id);
        return result;
    }


}
