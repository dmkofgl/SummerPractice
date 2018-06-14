package com.books.services;

import com.books.entities.Person;
import com.books.storage.abstracts.Repository;
import com.books.storage.concrete.AurthorRepository;

import java.util.List;

public class AuthorService {
    private Repository<Person> repository;

    public AuthorService() {
        repository = new AurthorRepository();
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
}
