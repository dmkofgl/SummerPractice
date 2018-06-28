package com.books.storage.abstracts;

import com.books.entities.Person;

public interface AuthorDAO extends DAO<Person> {
    Person getAuthorById(int id);
}
