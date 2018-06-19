package com.books.storage.abstracts;

import java.util.Collection;
import java.util.List;

public interface Repository<T> {


    public void add(T item);

    public void remove(T item);

    //public T remove(int id);

    public List<T> getCollection();

    public void setItem(int id, T item);

    //public void getItemById(int id);


}
