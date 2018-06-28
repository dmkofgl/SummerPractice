package com.books.storage.abstracts;

import com.books.entities.Book;

public interface BookDAO extends DAO<Book> {

    Book getBookById(int id);

}
