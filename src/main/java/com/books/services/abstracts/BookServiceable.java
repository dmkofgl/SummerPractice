package com.books.services.abstracts;

import com.books.entities.Book;

import java.util.Collection;
import java.util.List;

public interface BookServiceable {
    List<Book> filterByAuthorName(String part);

    List<Book> filterByAuthorName(Collection<Book> books, String part);

    List<Book> getAllBooks();

    Book getBookById(int id);

    void addBook(Book book);

    void saveBook(Book book);

    void removeAuthorBook(int bookId, int authorId);

    void changePublisher(int bookId, int publisherId);

    void removeBook(int bookId);

    void addAuthorBook(int bookId, int authorId);

}
