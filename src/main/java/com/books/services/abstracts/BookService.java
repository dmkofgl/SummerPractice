package com.books.services.abstracts;

import com.books.entities.Book;
import com.books.exceptions.UncorrectedQueryException;

import java.util.Collection;
import java.util.List;

public interface BookService {
    List<Book> filterByAuthorName(String part);

    List<Book> filterByAuthorName(Collection<Book> books, String part);

    List<Book> getAllBooks();

    Book getBookById(int id) throws UncorrectedQueryException;

    void addBook(Book book);

    void saveBook(Book book) throws UncorrectedQueryException;

    void removeAuthorBook(int bookId, int authorId) throws UncorrectedQueryException;

    void changePublisher(int bookId, int publisherId) throws UncorrectedQueryException;

    void removeBook(int bookId) ;

    void addAuthorBook(int bookId, int authorId) throws UncorrectedQueryException;

}
