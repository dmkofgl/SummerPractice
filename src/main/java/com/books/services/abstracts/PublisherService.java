package com.books.services.abstracts;

import com.books.entities.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    Optional<Publisher> getPublisherById(int id) throws IndexOutOfBoundsException;

    void savePublisher(int publisherId,Publisher publisher);
    void savePublisher(Publisher publisher);
    void addPublisher(Publisher publisher);
    void removePublisher(int publisherId);

    List<Publisher> getAllPublishers();
}
