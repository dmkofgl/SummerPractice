package com.books.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BookTest {
    private Book book;
    private Person[] authors;

    @Before
    public void setUp() {
        authors = new Person[]{new Person(0, "Pf1", "Pl1"),
                new Person(1, "Pf2", "Pl2")};
        book = new Book(0, "test1", new Date(),
                new Publisher(0, "testPublisher"), authors[0], authors[1]);
    }

    @Test
    public void getName() {
        Assert.assertEquals("test1", book.getName());
    }

    @Test
    public void removeAuthor() {
        book.removeAuthor(1);
        Assert.assertFalse(book.getAuthors().contains(authors[1]));

    }

    @Test
    public void equals() {
        Book book1 = new Book(0, "test1", new Date(),
                new Publisher(0, "testPublisher"), authors[0]);
        Book book2 = new Book(0, "test1", new Date(),
                new Publisher(0, "testPublisher"), authors[0]);
        assertTrue(book1.equals(book2));
    }
}