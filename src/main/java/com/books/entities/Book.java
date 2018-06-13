package com.books.entities;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Book {

    private int id;
    private String name;
    private Date publishDate;
    private List<Person> authors;
    private Publisher publisher;

    public Book(int id, String name, Date publishDate, Publisher publisher,
                Person... authors) {
        this(id, name, publishDate, publisher, Arrays.asList(authors));
    }

    public Book(int id, String name, Date publishDate, Publisher publisher,
                List<Person> authors) {
        this.id = id;
        this.name = name;
        this.publishDate = publishDate;
        this.authors = authors;
        this.publisher = publisher;
    }

    public Book() {
        this.name = "";
        this.publishDate = new Date();
        this.authors = new ArrayList<>();
        this.publisher = new Publisher();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public List<Person> getAuthors() {
        return authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setAuthors(List<Person> authors) {
        this.authors = authors;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int authorHash = authors.stream().mapToInt(author -> author.toString().hashCode()).sum();
        return name.hashCode() + publishDate.hashCode() + publisher.getName().hashCode() + authorHash;

    }

    @Override
    public boolean equals(Object obj) {
        Book otherBook;
        try {
            otherBook = (Book) obj;

        } catch (ClassCastException castException) {
            return false;
        }
        return
                this.hashCode() == otherBook.hashCode();
                /* name == otherBook.getName();
                && publishDate == otherBook.publishDate
                && publisher.getName() == otherBook.publisher.getName();
               && authors.stream().allMatch(author -> otherBook.authors.contains(author))
                && otherBook.authors.stream().allMatch(author -> authors.contains(author));*/
    }

    @Override
    public String toString() {
        return name +
                " " + publishDate +
                ", by " + authors.stream().map(author -> author.getFirstName() + " " + author.getLastName())
                .collect(Collectors.joining(", ")) +
                ", published by " + publisher.getName();
    }
}
