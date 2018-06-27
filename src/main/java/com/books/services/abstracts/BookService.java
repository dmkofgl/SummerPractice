package com.books.services.abstracts;

import com.books.entities.Book;

import java.util.List;

public interface BookService {
    List<Book> filterByAuthorName(String part);
    List<Book> getAllBooks();
    Book getBookById(int id);
    void addBook(Book book);
    void setBook(Book book);
    void removeAuthorBook(int bookId, int authorId);
    void changePublisher(int bookId, int publisherId);
}
