package com.books.services;

import com.books.entities.Publisher;
import com.books.storage.abstracts.Repository;
import com.books.storage.concrete.SQL.PublisherSQLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

public class PublisherService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    public static final PublisherService INSTANCE = new PublisherService();
    //TODO change it
    private PublisherSQLRepository repository;

    public static PublisherService getInstance() {
        return INSTANCE;
    }

    public PublisherService() {
        repository = PublisherSQLRepository.getInstance();
    }

    public Publisher getPublisherById(int id) throws IndexOutOfBoundsException {
        Publisher result = null;
        //TODO change to getById()
        try {
            result =repository.getPublisherById(id);
        } catch (NoSuchElementException e) {
            logger.info(String.format("no such element with id=%s in repository", id));
        }
        return result;
    }

    public List<Publisher> getAllPublishers() {
        return repository.getCollection();
    }
}
