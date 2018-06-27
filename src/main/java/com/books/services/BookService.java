package com.books.services;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.services.abstracts.BookServiceable;
import com.books.storage.abstracts.BookDAO;
import com.books.storage.concrete.SQL.BookSQLDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class BookService implements BookServiceable {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    private static final BookService INSTANCE = new BookService();

    private AuthorService authorService = AuthorService.getInstance();
    private BookDAO storage;

    private BookService() {
        storage = BookSQLDAO.getInstance();
    }


    public static BookService getInstance() {
        return INSTANCE;
    }

    PublisherService publisherService = PublisherService.getInstance();

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
            LOGGER.info(String.format("no such element with id=%s in storage", id));

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
    @Override
    public void addBookWithoutPublisher(Book book)
    {
        storage.addWithoutPublisher(book);
    }
}
