package com.books;

import java.util.*;

public class Main {
    private static List<Book> books;

    public static void main(String[] args) {
        books = new ArrayList<Book>();
        BookFilter filter = new BookFilter();
        Date date = new Date();
        Person[] authors = {
                new Person("Ivan", "First"),
                new Person("Andrew", "Second"),
                new Person("Sam", "Third"),
                new Person("Andrew", "Fourth")
        };
        books.add(new Book("first book", date, new Publisher("Publisher1"),
                           authors[0], authors[0], authors[1]));
        books.add(new Book("Second book", date, new Publisher("Publisher2"),
                           authors[1]));
        books.add(new Book("Third book", date, new Publisher("Publisher3"),
                           authors[2]));
        System.out.println(books);
        System.out.println(filter.filterByAuthorName(books,"nd"));

    }
}

