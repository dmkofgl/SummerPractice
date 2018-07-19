package com.books.dao.abstracts;

import com.books.entities.Publisher;

import java.util.Optional;

public interface PublisherDAO extends DAO<Publisher> {
    Optional<Publisher> getPublisherById( Integer id);

}
