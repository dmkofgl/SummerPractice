package com.books.dao.abstracts;

import com.books.entities.Person;

import java.util.List;

public interface AuthorDAO {
    Person getAuthorById(int id);
    void add(Person item);

    void remove(Person item);

    Person remove(int id);

    List<Person> getList();

    void saveItem(Integer id, Person item);
}
