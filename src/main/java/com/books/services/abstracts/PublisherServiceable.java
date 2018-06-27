package com.books.services.abstracts;

import com.books.entities.Publisher;

import java.util.List;

public interface PublisherServiceable {
    Publisher getPublisherById(int id) throws IndexOutOfBoundsException;

    List<Publisher> getAllPublishers();
}
