package com.books.storage.concrete;

import com.books.BookApplication;
import com.books.storage.abstracts.Repository;
import com.books.entities.Book;

import java.util.ArrayList;
import java.util.Collection;

public class BookRepository implements Repository<Book> {

    static Collection<Book> books;

    public Collection<Book> getBooks() {
        return books;
    }

    public BookRepository(Collection<Book> books) {
        this.books = books;
    }

    public BookRepository() {
        this.books = new ArrayList<>();
        //test
        BookApplication app = new BookApplication();
        app.main(null);
        this.books = BookApplication.getBooks();
    }

    @Override
    public void add(Book item) {
        books.add(item);
    }

    @Override
    public void remove(Book item) {
        books.remove(item);
    }


}
