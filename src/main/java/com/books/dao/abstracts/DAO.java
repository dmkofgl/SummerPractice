package com.books.dao.abstracts;

import java.util.List;

public interface DAO<T> {
    void add(T item);

    void remove(T item);

    T remove(int id);

    List<T> getList();

    void saveItem(Integer id, T item);


}
