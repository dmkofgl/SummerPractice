package com.books.services;

import com.books.entities.Person;
import com.books.storage.concrete.SQL.AuthorSQLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

public class AuthorService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    //TODO realize intermediate interface
    private AuthorSQLRepository repository;

    public static final AuthorService INSTANCE = new AuthorService();

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
        return repository.getCollection();
    }

    public Person getAuthorById(int id) throws IndexOutOfBoundsException {
        Person result = null;
        try {
            result = repository.getCollection().stream()
                    .filter(author -> author.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            logger.info(String.format("no such element with id=%s in repository", id));

        }
        return result;
    }


}
