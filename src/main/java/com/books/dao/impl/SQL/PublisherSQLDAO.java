package com.books.dao.impl.SQL;

import com.books.dao.abstracts.PublisherDAO;
import com.books.entities.Publisher;
import com.books.utils.PublisherTableColumnName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PublisherSQLDAO implements PublisherDAO {
    private static final String PUBLISHER_TABLE_NAME = "bookapp.publishers";

    private JdbcTemplate jdbcTemplate;

    public PublisherSQLDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        Publisher result = getPublisherById(id).get();
        String removeQuery = String.format("delete from %s where id = ?", PUBLISHER_TABLE_NAME);
        jdbcTemplate.update(removeQuery, id);
        return result;

    }

    @Override
    public List<Publisher> getList() {
        String getAllPublishers = String.format("select * from %s ", PUBLISHER_TABLE_NAME);
        List<Publisher> list;
        list = jdbcTemplate.query(getAllPublishers, new PublisherMapper());
        return list;
    }

    @Override
    public Optional<Publisher> getPublisherById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        String query = String.format("select * from %s where %s = ?", PUBLISHER_TABLE_NAME, PublisherTableColumnName.ID);
        Publisher publisher = jdbcTemplate.queryForObject(query, new Object[]{id}, new PublisherMapper());
        return Optional.ofNullable(publisher);
    }

    @Override
    public void saveItem(Integer id, Publisher item) {
        String setPublisherQuery = String.format("update %s set %s = ?,%s=? where %s =?",
                PUBLISHER_TABLE_NAME,
                PublisherTableColumnName.ID,
                PublisherTableColumnName.NAME,
                PublisherTableColumnName.ID);

        jdbcTemplate.update(setPublisherQuery, item.getId(), item.getName(), id);
    }

    private class PublisherMapper implements RowMapper<Publisher> {
        @Override
        public Publisher mapRow(ResultSet resultSet, int i) throws SQLException {
            Publisher publisher = new Publisher();
            Integer id = resultSet.getInt(PublisherTableColumnName.ID.toString());
            publisher.setId(id);
            String name = resultSet.getString(PublisherTableColumnName.NAME.toString());
            publisher.setName(name);
            return publisher;
        }
    }
}
