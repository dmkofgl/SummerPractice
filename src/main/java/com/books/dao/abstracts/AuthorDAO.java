package com.books.dao.abstracts;

import com.books.entities.Person;

public interface AuthorDAO extends DAO<Person> {
    Person getAuthorById(int id);
}
