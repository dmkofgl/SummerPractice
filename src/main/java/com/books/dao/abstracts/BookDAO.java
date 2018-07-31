package com.books.dao.abstracts;

import com.books.entities.Book;
import com.books.exceptions.UncorrectedQueryException;

import java.util.List;

public interface BookDAO {

    Book getBookById(int id) ;

    void add(Book item);

    void remove(Book item) ;

    Book remove(int id) ;

    List<Book> getList();

    void saveItem(Integer id, Book item);

}
