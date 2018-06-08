package com.books;

import java.util.*;

public class Main {
    private static Set<Book> books;

    public static void main(String[] args) {
        books = new HashSet<Book>() {
            @Override
            public String toString() {
                String result = "";
                Iterator<Book> iterator = iterator();
                while (iterator.hasNext()) {
                    Book book = iterator.next();
                    result += book + "\n";

                }
                return result;
            }
        };
        Date date = new Date();

        books.add(new Book("first book", date, new Person("Ivan", "First"), new Publisher("Publisher1")));
        books.add(new Book("Second book", date, new Person("Andrew", "Second"), new Publisher("Publisher2")));
        books.add(new Book("Third book", date, new Person("Sam", "Second"), new Publisher("Publisher3")));
        System.out.println(books);

    }

    public static Set<Book> filerByAuthorName(String name) {
        Set<Book> result = new HashSet<Book>();
        for (Book book : books) {
            if (book.getAuthor().getFirstName().contains(name))
                result.add(book);
        }
        return result;
    }
}
