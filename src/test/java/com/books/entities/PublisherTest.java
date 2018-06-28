package com.books.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class PublisherTest {

    @Test
    public void equals() {
        Publisher first = new Publisher(1,"qwerty");
        Publisher second = new Publisher(1,"qwerty");
        assertTrue(first.equals(second));
    }

    @Test
    public void hashCodeCheck() {
        Publisher first = new Publisher(1,"qwerty");
        Publisher second = new Publisher(1,"qwerty");
        assertEquals(first.hashCode(),second.hashCode());
    }
}