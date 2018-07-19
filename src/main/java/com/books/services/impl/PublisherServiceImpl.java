package com.books.services.impl;

import com.books.dao.abstracts.PublisherDAO;
import com.books.entities.Publisher;
import com.books.services.abstracts.PublisherService;

import java.util.List;
import java.util.Optional;

public class PublisherServiceImpl implements PublisherService {

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
