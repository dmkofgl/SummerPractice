package com.books.services.abstracts;

import com.books.entities.Person;

import java.util.List;

public interface AuthorServiceable {
    void addAuthor(Person person);

    void setPerson(int id, Person person);

    List<Person> getAllAuthors();

    Person getAuthorById(int id);
}
