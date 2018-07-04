package com.books.dao.concrete.SQL;

import com.books.dao.abstracts.PublisherDAO;
import com.books.entities.Publisher;
import com.books.utils.PublisherTableColumnName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PublisherSQLDAO implements PublisherDAO {
    private static final String PUBLISHER_TABLE_NAME = "bookapp.publishers";
    private static final Logger logger = LoggerFactory.getLogger(PublisherSQLDAO.class);

    private JdbcTemplate jdbcTemplate;


    public PublisherSQLDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate =jdbcTemplate;
    }

    @Override
    public void add(Publisher item) {
        String addQuery = String.format("insert into %s (%s) values(?)",
                PUBLISHER_TABLE_NAME,
                PublisherTableColumnName.NAME.toString());
        jdbcTemplate.update(addQuery, new Object[]{item.getName()});
    }

    @Override
    public void remove(Publisher item) {
        remove(item.getId());
    }

    @Override
    public Publisher remove(int id) {
        Publisher result = getPublisherById(id);
        String removeQuery = String.format("delete from %s where id = ?", PUBLISHER_TABLE_NAME);
        jdbcTemplate.update(removeQuery, new Object[]{id});
        return result;

    }

    @Override
    public List<Publisher> getList() {
        String getAllPublishers = String.format("select * from %s ", PUBLISHER_TABLE_NAME);

        return jdbcTemplate.query(getAllPublishers, (rs, rn) -> {
            Publisher publisher = new Publisher();
            Integer id = rs.getInt(PublisherTableColumnName.ID.toString());
            publisher.setId(id);
            String name = rs.getString(PublisherTableColumnName.NAME.toString());
            publisher.setName(name);
            return publisher;
        });
    }

    @Override
    public Publisher getPublisherById(int id) {
        String query = String.format("select * from %s where %s = ?", PUBLISHER_TABLE_NAME, PublisherTableColumnName.ID);
        return jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rn) -> {
            Publisher publisher = new Publisher();
            publisher.setId(id);
            String name = rs.getString(PublisherTableColumnName.NAME.toString());
            publisher.setName(name);
            return publisher;
        });
    }

    @Override
    public void saveItem(Integer id, Publisher item) {
        String setPublisherQuery = String.format("update %s set %s = ?,%s=? where %s =?",
                PUBLISHER_TABLE_NAME,
                PublisherTableColumnName.ID,
                PublisherTableColumnName.NAME,
                PublisherTableColumnName.ID);

        jdbcTemplate.update(setPublisherQuery, new Object[]{item.getId(), item.getName(), id});
    }
}
