package com.books.services;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.storage.concrete.BookRepository;

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
        BookRepository repository = new BookRepository();
        return repository.getCollection();


    }


}
