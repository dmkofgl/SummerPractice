package com.books.services.abstracts;

import com.books.entities.Publisher;
import com.books.exceptions.UncorrectedQueryException;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    Optional<Publisher> getPublisherById(int id) throws UncorrectedQueryException;

    void savePublisher(int publisherId, Publisher publisher) throws UncorrectedQueryException;

    void savePublisher(Publisher publisher) throws UncorrectedQueryException;

    void addPublisher(Publisher publisher);

    void removePublisher(int publisherId) throws UncorrectedQueryException;

    List<Publisher> getAllPublishers();
}
