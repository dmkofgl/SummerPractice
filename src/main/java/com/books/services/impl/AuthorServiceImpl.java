package com.books.services.impl;

import com.books.dao.abstracts.AuthorDAO;
import com.books.entities.Person;
import com.books.services.abstracts.AuthorService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorServiceImpl implements AuthorService {

    private AuthorDAO authorDAO;

    private AuthorServiceImpl(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    public void addAuthor(Person person) {
        authorDAO.add(person);
    }

    @Override
    public void saveAuthor(int id, Person person) {
        authorDAO.saveItem(id, person);
    }

    @Override
    public void saveAuthor(Person person) {
        saveAuthor(person.getId(), person);
    }

    @Override
    public void removeAuthor(int personId) {
        authorDAO.remove(personId);
    }

    @Override
    public List<Person> getAllAuthors() {
        return authorDAO.getList();
    }

    @Override
    public List<Person> getResidualAuthors(Collection<Person> without) {
        return getAllAuthors().stream().filter(author -> !(without.contains(author))).collect(Collectors.toList());
    }

    @Override
    public Person getAuthorById(int id) {
        Person result;
        result = authorDAO.getAuthorById(id);
        return result;
    }


}
