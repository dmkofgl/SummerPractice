package com.books.services.impl;

import com.books.dao.abstracts.BookDAO;
import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.exceptions.UncorrectedQueryException;
import com.books.services.abstracts.AuthorService;
import com.books.services.abstracts.BookService;
import com.books.services.abstracts.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    private AuthorService authorService;
    private PublisherService publisherService;
    private BookDAO bookDAO;

    private BookServiceImpl(BookDAO bookDAO, AuthorService authorService, PublisherService publisherService) {
        this.bookDAO = bookDAO;
        this.authorService = authorService;
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
        return bookDAO.getList();
    }

    @Override
    public Book getBookById(int id) throws UncorrectedQueryException {
        Book result = null;
        try {
            result = bookDAO.getBookById(id);
        } catch (UncorrectedQueryException e) {
            LOGGER.error(e.getMessage());
            LOGGER.info(String.format("no such element with id=%s in dao", id));
            throw e;

        }
        return result;
    }

    @Override
    public void addBook(Book book) {
        bookDAO.add(book);
    }

    @Override
    public void saveBook(Book book) throws UncorrectedQueryException {
        bookDAO.saveItem(book.getId(), book);
    }

    @Override
    public void removeAuthorBook(int bookId, int authorId) throws UncorrectedQueryException {
        Book book = getBookById(bookId);
        book.removeAuthor(authorId);
        saveBook(book);
    }

    @Override
    public void changePublisher(int bookId, int publisherId) throws UncorrectedQueryException {
        Book book;
        try {
            book = getBookById(bookId);
        } catch (UncorrectedQueryException e) {
            return;
        }
        Optional<Publisher> publisher = publisherService.getPublisherById(publisherId);
        publisher.ifPresent(book::setPublisher);
        saveBook(book);
    }

    @Override
    public void removeBook(int bookId) {
        bookDAO.remove(bookId);
    }

    @Override
    public void addAuthorBook(int bookId, int authorId) throws UncorrectedQueryException {
        Book book = null;
        book = getBookById(bookId);
        Person author = authorService.getAuthorById(authorId);
        book.addAuthor(author);
        saveBook(book);
    }

}
