package com.books;

import java.util.Date;

public class Book {
    private String name;

    public String getName() {
        return name;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public Person getAuthor() {
        return author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    private Date publishDate;
    private Person author;
    private Publisher publisher;

    public Book(String name, Date publishDate, Person author, Publisher publisher) {
        this.name = name;
        this.publishDate = publishDate;
        this.author = author;
        this.publisher = publisher;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return name +
                " " + publishDate +
                ", by " + author +
                ", published by " + publisher;
    }
}
