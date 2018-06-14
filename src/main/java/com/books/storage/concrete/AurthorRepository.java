package com.books.storage.concrete;

import com.books.entities.Person;
import com.books.storage.abstracts.Repository;

import java.util.ArrayList;
import java.util.List;

public class AurthorRepository implements Repository<Person> {
    private static List<Person> authors;

    @Override
    public void add(Person item) {
        authors.add(item);

    }

    @Override
    public void remove(Person item) {
        authors.remove(item);
    }

    @Override
    public List<Person> getCollection() {
        return authors;
    }

    @Override
    public void setItem(int id, Person item) {
        authors.set(id, item);
    }

    static {
        authors = new ArrayList<Person>();
        authors.add(new Person("Ivan", "First"));
        authors.add(new Person("Andrew", "Second"));
        authors.add(new Person("Sam", "Third"));
        authors.add(new Person("Andrew", "Fourth"));

    }
}
