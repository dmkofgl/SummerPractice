package com.books.storage.concrete;

import com.books.entities.Publisher;
import com.books.storage.abstracts.DAO;

import java.util.List;

public class PublisherDAO implements DAO<Publisher> {
    private static List<Publisher> publishers;

    @Override
    public void add(Publisher item) {
        publishers.add(item);
    }

    @Override
    public void remove(Publisher item) {
        publishers.remove(item);
    }

    // TODO fix
    @Override
    public Publisher remove(int id) {

        Publisher publisher = publishers.stream().filter(publish -> publish.getId() == id).findFirst().get();
        publishers.remove(publisher);
        return publisher;
    }

    @Override
    public List<Publisher> getList() {
        return publishers;
    }

    @Override
    public void saveItem(int id, Publisher item) {
        publishers.set(id, item);
    }
}
