package com.books.services;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;

import java.util.*;
import java.util.stream.Collectors;

public class BookService {

    public List<Book> filterByAuthorName(Collection<Book> books, String part) {
        return books.stream().filter((book -> book.getAuthors().stream()
                .map(author -> author.getFirstName().toLowerCase() + " " + author.getLastName().toLowerCase())
                .anyMatch(authorInfo -> authorInfo.contains(part.toLowerCase()))))
                .collect(Collectors.toList());

    }

    public List<Book> filterByAuthorName(Book[] books, String part) {
        return filterByAuthorName(Arrays.asList(books), part);
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        Date date = new Date();
        Person[] authors = new Person[]{
                new Person("Ivan", "First"),
                new Person("Andrew", "Second"),
                new Person("Sam", "Third"),
                new Person("Andrew", "Fourth")
        };
        books = new ArrayList<Book>();
        books.add(new Book("first book", date, new Publisher("Publisher1"),
                authors[0], authors[1]));
        books.add(new Book("Second book", date, new Publisher("Publisher2"),
                authors[1]));
        books.add(new Book("Third book", date, new Publisher("Publisher3"),
                authors[2]));
        books.add(new Book("Third book", date, new Publisher("Publisher4"),
                authors[3]));
        return  books;
    }


}
