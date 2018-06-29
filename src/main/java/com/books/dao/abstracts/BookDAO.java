package com.books.dao.abstracts;

import com.books.entities.Book;

public interface BookDAO extends DAO<Book> {

    Book getBookById(int id);

}
