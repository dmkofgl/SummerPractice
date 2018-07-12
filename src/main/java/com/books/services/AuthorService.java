package com.books.services;

import com.books.dao.abstracts.AuthorDAO;
import com.books.entities.Person;
import com.books.services.abstracts.AuthorServiceable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Person> getReducedAuthors(Collection<Person> without) {
        return getAllAuthors().stream().filter(author -> !(without.contains(author))).collect(Collectors.toList());
    }

    @Override
    public Person getAuthorById(int id) {
        Person result = null;
        result = storage.getAuthorById(id);
        return result;
    }


}
