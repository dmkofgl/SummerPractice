package com.books.dao.abstracts;

import com.books.entities.Publisher;
import com.books.exceptions.UncorrectedQueryException;

import java.util.List;
import java.util.Optional;

public interface PublisherDAO  {
    Optional<Publisher> getPublisherById( Integer id)throws UncorrectedQueryException;
    void add(Publisher item);

    void remove(Publisher item)throws UncorrectedQueryException;

    Publisher remove(int id)throws UncorrectedQueryException;

    List<Publisher> getList();

    void saveItem(Integer id, Publisher item)throws UncorrectedQueryException;

}
