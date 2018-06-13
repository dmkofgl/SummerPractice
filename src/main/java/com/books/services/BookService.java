package com.books.services;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.storage.abstracts.Repository;
import com.books.storage.concrete.BookRepository;

import java.util.*;
import java.util.stream.Collectors;

public class BookService {
    private static Repository<Book> repository;

    static {
        repository = new BookRepository();
    }

    public List<Book> filterByAuthorName(Collection<Book> books, String part) {
        return books.stream().filter((book -> book.getAuthors().stream()
                .map(author -> author.getFirstName().toLowerCase() + " " + author.getLastName().toLowerCase())
                .anyMatch(
                        authorInfo -> authorInfo.contains(part.toLowerCase()))))
                .collect(Collectors.toList());

    }

    public List<Book> filterByAuthorName(Book[] books, String part) {
        return filterByAuthorName(Arrays.asList(books), part);
    }

    public List<Book> getAllBooks() {
        return repository.getCollection();
    }

    public Book getBookById(int id)throws IndexOutOfBoundsException {
        return repository.getCollection().stream()
                .filter(book -> book.getId() == id).findFirst().get();
    }

    public void addBook(Book book) {
        repository.add(book);
    }

    public void setBook(int id, Book book) {
        repository.setItem(id, book);
    }
}
