package com.books.storage.abstracts;

public interface Repository<T> {
    // void or boolean?
    public void add(T item);
    public void remove(T item);

}
