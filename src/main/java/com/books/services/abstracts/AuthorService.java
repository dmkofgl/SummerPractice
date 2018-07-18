package com.books.services.abstracts;

import com.books.entities.Person;

import java.util.Collection;
import java.util.List;

public interface AuthorService {
    void addAuthor(Person person);

    void setPerson(int id, Person person);

    List<Person> getAllAuthors();
    List<Person> getResidualAuthors(Collection<Person> without);

    Person getAuthorById(int id);

}