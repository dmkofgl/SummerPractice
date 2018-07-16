package com.books.dao.concrete;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.dao.abstracts.BookDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookDAOEmbedded implements BookDAO {

    private static List<Book> books;
    private static final Logger logger = LoggerFactory.getLogger(BookDAOEmbedded.class);

    public BookDAOEmbedded(List<Book> books) {
        this.books = books;
    }

    private BookDAOEmbedded() {

    }

    static {
        books = new ArrayList<>();
        final Date date = new Date();
        List<Person> authors = new ArrayList<Person>();
        authors.add(new Person(0, "Ivan", "First"));
        authors.add(new Person(1, "Andrew", "Second"));
        authors.add(new Person(2, "Sam", "Third"));
        authors.add(new Person(3, "Andrew", "Fourth"));
        books = new ArrayList<Book>();
        books.add(new Book(0, "first book", date, new Publisher(0, "Publisher1"),
                authors.get(0), authors.get(1)));
        books.add(new Book(1, "Second book", date, new Publisher(1, "Publisher2"),
                authors.get(1)));
        books.add(new Book(2, "Third book", date, new Publisher(2, "Publisher3"),
                authors.get(2)));
        books.add(new Book(3, "Third book", date, new Publisher(3, "Publisher4"),
                authors.get(3)));
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
    public Book remove(int id) {
        Book result = getBookById(id);
        books.remove(result);
        return result;
    }

    @Override
    public List<Book> getList() {
        return books;
    }

    @Override
    public void saveItem(Integer id, Book item) {

        if (books.stream().anyMatch(book -> book.getId() == id)) {
            logger.info("set item in BookDAOEmbedded:" + item);
            books.set(id, item);
        } else {
            logger.info("add item in BookDAOEmbedded:" + item);
            books.add(item);
        }
    }

    @Override
    public Book getBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst().get();
    }
}
