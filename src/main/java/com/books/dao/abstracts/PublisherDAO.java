package com.books.dao.abstracts;

import com.books.entities.Publisher;

public interface PublisherDAO extends DAO<Publisher> {
     Publisher getPublisherById(int id);

}
