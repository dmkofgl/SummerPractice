package com.books.services;

import com.books.dao.abstracts.BookDAO;
import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.services.abstracts.AuthorServiceable;
import com.books.services.abstracts.BookServiceable;
import com.books.services.abstracts.PublisherServiceable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class BookService implements BookServiceable {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    private AuthorServiceable authorService;
    private PublisherServiceable publisherService;
    private BookDAO storage;

    private BookService(BookDAO bookDAO, AuthorServiceable authorServiceable, PublisherServiceable publisherService) {
        storage = bookDAO;
        authorService = authorServiceable;
        this.publisherService = publisherService;
    }


    @Override
    public List<Book> filterByAuthorName(String part) {
        return filterByAuthorName(getAllBooks(), part);
    }

    @Override
    public List<Book> filterByAuthorName(Collection<Book> books, String part) {
        return books.stream().filter((book -> book.getAuthors().stream()
                .map(author -> author.getFirstName().toLowerCase() + " " + author.getLastName().toLowerCase())
                .anyMatch(authorInfo -> authorInfo.contains(part.toLowerCase()))))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAllBooks() {
        return storage.getList();
    }

    @Override
    public Book getBookById(int id) {
        Book result = null;
        try {
            result = storage.getBookById(id);
        } catch (NoSuchElementException e) {
            LOGGER.info(String.format("no such element with id=%s in dao", id));

        }
        return result;
    }

    @Override
    public void addBook(Book book) {
        storage.add(book);
    }

    @Override
    public void saveBook(Book book) {
        storage.saveItem(book.getId(), book);
    }

    @Override
    public void removeAuthorBook(int bookId, int authorId) {
        Book book = getBookById(bookId);
        book.removeAuthor(authorId);
        saveBook(book);
    }

    @Override
    public void changePublisher(int bookId, int publisherId) {
        Book book = getBookById(bookId);
        Publisher publisher = publisherService.getPublisherById(publisherId);
        book.setPublisher(publisher);
        saveBook(book);
    }

    @Override
    public void removeBook(int bookId) {
        storage.remove(bookId);
    }

    @Override
    public void addAuthorBook(int bookId, int authorId) {
        Book book = getBookById(bookId);

        Person author = authorService.getAuthorById(authorId);
        book.addAuthor(author);
        saveBook(book);
    }

}
