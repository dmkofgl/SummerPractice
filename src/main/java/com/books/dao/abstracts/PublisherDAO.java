package com.books.dao.abstracts;

import com.books.entities.Publisher;
import com.books.exceptions.UncorrectedQueryException;

import java.util.List;
import java.util.Optional;

public interface PublisherDAO {
    Optional<Publisher> getPublisherById(Integer id);

    void add(Publisher item);

    void remove(Publisher item);

    Publisher remove(int id);

    List<Publisher> getList();

    void saveItem(Integer id, Publisher item);

}
