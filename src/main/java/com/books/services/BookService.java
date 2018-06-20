package com.books.services;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.storage.abstracts.Repository;
import com.books.storage.concrete.SQL.BookSQLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private static Repository<Book> repository;

    static {
        //  repository = new BookRepository();
        repository = BookSQLRepository.getInstance();
    }

    public List<Book> filterByAuthorName(String part) {
        return filterByAuthorName(getAllBooks(), part);
    }

    public List<Book> filterByAuthorName(Collection<Book> books, String part) {
        return books.stream().filter((book -> book.getAuthors().stream()
                .map(author -> author.getFirstName().toLowerCase() + " " + author.getLastName().toLowerCase())
                .anyMatch(
                        authorInfo -> authorInfo.contains(part.toLowerCase()))))
                .collect(Collectors.toList());
    }

    public int generateId() {
        List<Integer> booksId = repository.getCollection().stream().map(book -> book.getId()).collect(Collectors.toList());
        int id = 0;
        while (booksId.contains(id)) {
            id++;
        }
        return id;
    }

    public List<Book> filterByAuthorName(Book[] books, String part) {
        return filterByAuthorName(Arrays.asList(books), part);
    }

    public List<Book> getAllBooks() {
        return repository.getCollection();
    }

    public Book getBookById(int id) throws IndexOutOfBoundsException {
        Book result = null;
        try {
            result = repository.getCollection().stream()
                    .filter(book -> book.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            logger.info(String.format("no such element with id=%s in repository", id));

        }
        return result;
    }

    public void addBook(Book book) {
        repository.add(book);
    }

    public void setBook(Book book) {
        repository.setItem(book.getId(), book);
    }

    public void removeAuthorBook(int bookId, int authorId) {
        Book book = getBookById(bookId);
        book.removeAuthor(authorId);
        setBook(book);
    }

    public void removeBook(int bookId) {
        repository.remove(bookId);
    }

    public void addAuthorBook(int bookId, int authorId) {
        Book book = getBookById(bookId);
        AuthorService authorService = new AuthorService();
        Person author = authorService.getAuthorById(authorId);
        //TODO why it doesn't add? (doesn't save in DB)
        // book.getAuthors().add(author);
        book.addAuthor(author);
        setBook(book);

    }
}
