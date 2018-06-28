package com.books.storage.concrete;

import com.books.entities.Person;
import com.books.storage.abstracts.DAO;

import java.util.ArrayList;
import java.util.List;

public class AuthorDAO implements DAO<Person> {
    private static List<Person> authors;

    static {
        authors = new ArrayList<Person>();
        authors.add(new Person(0, "Ivan", "First"));
        authors.add(new Person(1, "Andrew", "Second"));
        authors.add(new Person(2, "Sam", "Third"));
        authors.add(new Person(3, "Andrew", "Fourth"));

    }

    @Override
    public void add(Person item) {
        authors.add(item);
    }

    @Override
    public void remove(Person item) {
        authors.remove(item);
    }

    @Override
    public Person remove(int id) {

        Person result = authors.stream().filter(author -> author.getId() == id).findFirst().get();
        authors.remove(result);
        return result;
    }

    @Override
    public List<Person> getList() {
        return authors;
    }

    @Override
    public void saveItem(Integer id, Person item) {
        authors.set(id, item);
    }


}
