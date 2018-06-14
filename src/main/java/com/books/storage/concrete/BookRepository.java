package com.books.storage.concrete;

import com.books.BookApplication;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.storage.abstracts.Repository;
import com.books.entities.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BookRepository implements Repository<Book> {

    private static List<Book> books;
    private static final Logger logger = LoggerFactory.getLogger(BookApplication.class);


    public BookRepository(List<Book> books) {
        this.books = books;
    }

    public BookRepository() {

    }
    static{
        books = new ArrayList<>();
        final Date date = new Date();
        Person[] authors = new Person[]{
                new Person("Ivan", "First"),
                new Person("Andrew", "Second"),
                new Person("Sam", "Third"),
                new Person("Andrew", "Fourth")
        };
        books = new ArrayList<Book>();
        books.add(new Book(0, "first book", date, new Publisher("Publisher1"),
                           authors[0], authors[1]));
        books.add(new Book(1, "Second book", date, new Publisher("Publisher2"),
                           authors[1]));
        books.add(new Book(2, "Third book", date, new Publisher("Publisher3"),
                           authors[2]));
        books.add(new Book(3, "Third book", date, new Publisher("Publisher4"),
                           authors[3]));
    }



    @Override
    public void add(Book item) {
        books.add(item);
    }

    @Override
    public void remove(Book item) {
        books.remove(item);
    }

    @Override
    public List<Book> getCollection() {
        return books;
    }

    @Override
    public void setItem(int id, Book item) {

        if (books.stream().anyMatch(book -> book.getId() ==id)) {
            logger.info("set item in BookRepository:" + item);
            books.set(id, item);
        } else {
            logger.info("add item in BookRepository:" + item);
            books.add(item);
        }
    }


}
