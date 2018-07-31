package com.books.services.impl;

import com.books.dao.abstracts.PublisherDAO;
import com.books.entities.Publisher;
import com.books.exceptions.UncorrectedQueryException;
import com.books.services.abstracts.PublisherService;

import java.util.List;
import java.util.Optional;

public class PublisherServiceImpl implements PublisherService {

    private PublisherDAO publisherDAO;


    public PublisherServiceImpl(PublisherDAO publisherDAO) {
        this.publisherDAO = publisherDAO;
    }

    @Override
    public Optional<Publisher> getPublisherById(int id) throws UncorrectedQueryException {
        Optional<Publisher> result = publisherDAO.getPublisherById(id);
        return result;
    }

    @Override
    public void savePublisher(int publisherId, Publisher publisher)throws UncorrectedQueryException {
        publisherDAO.saveItem(publisherId, publisher);
    }

    @Override
    public void savePublisher(Publisher publisher) throws UncorrectedQueryException{
        savePublisher(publisher.getId(), publisher);
    }

    @Override
    public void addPublisher(Publisher publisher) {
        publisherDAO.add(publisher);
    }

    @Override
    public void removePublisher(int publisherId)throws UncorrectedQueryException {
        publisherDAO.remove(publisherId);
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherDAO.getList();
    }
}
