package com.books.services;

import com.books.entities.Publisher;
import com.books.storage.abstracts.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

public class PublisherService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    Repository<Publisher> repository = null;

    public Publisher getPublisherById(int id) throws IndexOutOfBoundsException {
        Publisher result = null;
        try {
            result = repository.getCollection().stream()
                    .filter(publisher -> publisher.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            logger.info(String.format("no such element with id=%s in repository", id));

        }
        return result;
    }
}
