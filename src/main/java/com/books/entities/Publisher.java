package com.books.entities;

public class Publisher {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Publisher(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
