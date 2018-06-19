package com.books.entities;

public class Person {


    private int id;
    private String firstName;
    private String lastName;

    public Person(int id,String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public int hashCode() {
        return id + firstName.hashCode()+lastName.hashCode();
    }

    public boolean equals(Person obj) {
        return id == obj.getId();

    }
    public boolean equals(Object obj) {
        return id == ((Person)obj).getId();

    }
}
