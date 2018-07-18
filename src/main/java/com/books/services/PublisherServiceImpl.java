package com.books.services;

import com.books.entities.Publisher;
import com.books.dao.abstracts.PublisherDAO;
import com.books.services.abstracts.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class PublisherServiceImpl implements PublisherService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private PublisherDAO publisherDAO;


    public PublisherServiceImpl(PublisherDAO publisherDAO) {
        this.publisherDAO = publisherDAO;
    }

    @Override
    public Optional<Publisher> getPublisherById(int id) throws IndexOutOfBoundsException {
        Optional<Publisher> result = publisherDAO.getPublisherById(id);
        return result;
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherDAO.getList();
    }
}
