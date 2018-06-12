package com.books;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.services.BookService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookApplication {

    public static void main(String[] args) {
        BookService filter = new BookService();
        Date date = new Date();
        Person[] authors = new Person[]{
                new Person("Ivan", "First"),
                new Person("Andrew", "Second"),
                new Person("Sam", "Third"),
                new Person("Andrew", "Fourth")
        };
        List<Book> books = new ArrayList<Book>();
        books.add(new Book("first book", date, new Publisher("Publisher1"),
                authors[0], authors[1]));

        books.add(new Book("Second book", date, new Publisher("Publisher2"),
                authors[1]));
        books.add(new Book("Third book", date, new Publisher("Publisher3"),
                authors[2]));
        books.add(new Book("Third book", date, new Publisher("Publisher4"),
                authors[3]));

        System.out.println(books);
        System.out.println(filter.filterByAuthorName(books, "an"));

    }
}

