package com.books.storage.abstracts;

import com.books.entities.Book;

public interface BookDAO extends DAO<Book> {
    void addWithoutPublisher(Book item);

    Book getBookById(int id);

}
