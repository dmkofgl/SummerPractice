package com.books;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookApplication {

    private static final Logger logger = LoggerFactory.getLogger(BookApplication.class);

    static List<Book> books;

    public static void main(String[] args) {
        BookService filter = new BookService();
        Date date = new Date();
        List<Person> authors = new ArrayList<>();

        authors.add(new Person(0, "Ivan", "First"));
        authors.add(new Person(1, "Andrew", "Second"));
        authors.add(new Person(2, "Sam", "Third"));
        authors.add(new Person(3, "Andrew", "Fourth"));

        books = new ArrayList<Book>();
        books.add(new Book(0, "first book", date, new Publisher("Publisher1"),
                authors.get(0), authors.get(1)));
        books.add(new Book(1, "Second book", date, new Publisher("Publisher2"),
                authors.get(1)));
        books.add(new Book(2, "Third book", date, new Publisher("Publisher3"),
                authors.get(2)));
        books.add(new Book(3, "Third book", date, new Publisher("Publisher4"),
                authors.get(3)));

        logger.info("View books");
        logger.debug("{}", books);
        logger.info("View filtered by author name books that contains 'an'");
        logger.debug("{}", books);
        logger.debug("{}", filter.filterByAuthorName(books, "an"));
        Book b = books.get(0);
        List<Person> l = new ArrayList<>(b.getAuthors());
        Person r = l.remove(1);
        System.out.println(r);

    }

    public static List<Book> getBooks() {
        return books;
    }


}

