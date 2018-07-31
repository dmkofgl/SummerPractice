package com.books.services.abstracts;

import com.books.entities.Person;

import java.util.Collection;
import java.util.List;

public interface AuthorService {
    void addAuthor(Person person);

    void saveAuthor(int id, Person person);

    void saveAuthor(Person person);

    void removeAuthor(int personId);

    List<Person> getAllAuthors();

    List<Person> getResidualAuthors(Collection<Person> without);

    Person getAuthorById(int id);

}
