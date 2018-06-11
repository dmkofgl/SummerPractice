package com.books.entities;

import java.util.*;
import java.util.stream.Collectors;

public class Book {

    private String name;
    private Date publishDate;
    private Set<Person> authors;
    private Publisher publisher;

    public Book(String name, Date publishDate, Publisher publisher,
                Person... authors) {
        this.name = name;
        this.publishDate = publishDate;
        this.authors = new HashSet<Person>(Arrays.asList(authors)) {
            @Override
            public String toString() {
                String result = "";
                Iterator<Person> iterator = iterator();
                while (iterator.hasNext()) {
                    Person author = iterator.next();
                    result += author + ", ";

                }
                return result;
            }
        };
        this.publisher = publisher;
    }


    public String getName() {
        return name;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public Set<Person> getAuthors() {
        return authors;
    }

    public Publisher getPublisher() {
        return publisher;
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
