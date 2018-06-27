package com.books.storage.concrete;

import com.books.entities.Publisher;
import com.books.storage.abstracts.Repository;

import java.util.List;

public class PublisherRepository implements Repository<Publisher> {
    static List<Publisher> publishers;

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
        return null;
    }

    @Override
    public List<Publisher> getList() {
        return publishers;
    }

    @Override
    public void setItem(int id, Publisher item) {
        publishers.set(id, item);
    }
}
