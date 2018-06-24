package com.books.storage.abstracts;

import com.books.entities.Publisher;

public interface PublisherDAO extends Repository<Publisher> {
    public Publisher getPublisherById(int id);

}
