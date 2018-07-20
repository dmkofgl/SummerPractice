package com.books.dao.abstracts;

import com.books.entities.Book;
import com.books.exceptions.UncorrectedQueryException;

import java.util.List;

public interface BookDAO {

    Book getBookById(int id) throws UncorrectedQueryException;

    void add(Book item);

    void remove(Book item) throws UncorrectedQueryException;

    Book remove(int id) throws UncorrectedQueryException;

    List<Book> getList();

    void saveItem(Integer id, Book item);

}
