package com.books.services;

import com.books.entities.Publisher;
import com.books.dao.abstracts.PublisherDAO;
import com.books.dao.concrete.SQL.PublisherSQLDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

public class PublisherService implements com.books.services.abstracts.PublisherServiceable {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    private PublisherDAO storage;


    public PublisherService(PublisherDAO publisherDAO) {
        storage = publisherDAO;
    }

    @Override
    public Publisher getPublisherById(int id) throws IndexOutOfBoundsException {
        Publisher result = null;
        try {
            result = storage.getPublisherById(id);
        } catch (NoSuchElementException e) {
            logger.info(String.format("no such element with id=%s in dao", id));
        }
        return result;
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return storage.getList();
    }
}
