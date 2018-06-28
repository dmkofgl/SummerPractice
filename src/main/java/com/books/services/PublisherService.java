package com.books.services;

import com.books.entities.Publisher;
import com.books.storage.abstracts.PublisherDAO;
import com.books.storage.concrete.SQL.PublisherSQLDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

public class PublisherService implements com.books.services.abstracts.PublisherServiceable {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    private static final PublisherService INSTANCE = new PublisherService();

    private PublisherDAO storage;

    public static PublisherService getInstance() {
        return INSTANCE;
    }

    public PublisherService() {
        storage = PublisherSQLDAO.getInstance();
    }

    @Override
    public Publisher getPublisherById(int id) throws IndexOutOfBoundsException {
        Publisher result = null;
        try {
            result = storage.getPublisherById(id);
        } catch (NoSuchElementException e) {
            logger.info(String.format("no such element with id=%s in storage", id));
        }
        return result;
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return storage.getList();
    }
}
