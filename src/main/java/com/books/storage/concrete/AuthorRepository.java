package com.books.storage.concrete;

import com.books.entities.Person;
import com.books.storage.abstracts.Repository;

import java.util.ArrayList;
import java.util.List;

public class AuthorRepository implements Repository<Person> {
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
//TODO fix
    @Override
    public Person remove(int id) {
        return null;
    }

    @Override
    public List<Person> getList() {
        return authors;
    }

    @Override
    public void setItem(int id, Person item) {
        authors.set(id, item);
    }


}
