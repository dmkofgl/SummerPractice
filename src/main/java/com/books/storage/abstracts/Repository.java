package com.books.storage.abstracts;

import java.util.Collection;
import java.util.List;

public interface Repository<T> {

    // void or boolean?
    public void add(T item);

    public void remove(T item);

    public List<T> getCollection();

    public void setItem(int id, T item);


}
