package com.books.entities;

import java.util.*;
import java.util.stream.Collectors;

public class Book {

    private Integer id;
    private String name;
    private Date publishDate;
    private List<Person> authors;
    private Publisher publisher;

    public Book(int id, String name, Date publishDate, Publisher publisher,
                Person... authors) {
        this(id, name, publishDate, publisher, new ArrayList<>(Arrays.asList(authors)));
    }

    public Book(int id, String name, Date publishDate, Publisher publisher,
                Collection<Person> authors) {
        this.id = id;
        this.name = name;
        this.publishDate = publishDate;
        this.authors = new ArrayList<>(authors);
        this.publisher = publisher;
    }

    public Book() {
        this.name = "";
        this.publishDate = new Date();
        this.authors = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getPublishDate() {
        return publishDate;
    }


    public Collection<Person> getAuthors() {
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

    public void setAuthors(Collection<Person> authors) {
        this.authors = new ArrayList<>(authors);
    }

    public void addAuthor(Person author) {
        authors.add(author);
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void removeAuthor(int authorId) {
        Person removedAuthor = authors.stream().filter(person -> person.getId() == authorId).findFirst().get();
        authors.remove(removedAuthor);
    }

    public boolean equals(Book book) {

        return id == book.id &&
                Objects.equals(name, book.name) &&
                Objects.equals(publishDate, book.publishDate) &&
                Objects.equals(authors, book.authors) &&
                Objects.equals(publisher, book.publisher);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return equals(book);

    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, publishDate, authors, publisher);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append(" " + publishDate);
        stringBuilder.append(", by ");
        stringBuilder.append(authors.stream().map(author -> author.getFirstName() + " " + author.getLastName())
                .collect(Collectors.joining(", ")));
        stringBuilder.append(", published by ");
        stringBuilder.append(publisher == null ? "" : publisher.getName());

        return stringBuilder.toString();
    }
}
