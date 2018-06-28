package com.books.entities;

import java.util.Objects;

public class Publisher {


    private Integer id = null;
    private String name;

    public Publisher(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Publisher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return id == publisher.id &&
                Objects.equals(name, publisher.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
