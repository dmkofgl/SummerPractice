package com.books.storage.abstracts;

import com.books.entities.Book;

public interface BookDAO extends Repository<Book> {
    Book getBookById(int id);

}
