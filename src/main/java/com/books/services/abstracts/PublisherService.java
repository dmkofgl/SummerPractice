package com.books.services.abstracts;

import com.books.entities.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    Optional<Publisher> getPublisherById(int id) throws IndexOutOfBoundsException;

    List<Publisher> getAllPublishers();
}