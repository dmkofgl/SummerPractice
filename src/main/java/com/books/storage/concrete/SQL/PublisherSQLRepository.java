package com.books.storage.concrete.SQL;

import com.books.entities.Publisher;
import com.books.storage.abstracts.PublisherDAO;
import com.books.storage.abstracts.Repository;
import com.books.utils.Constants;
import com.books.utils.PublisherTableColumnName;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublisherSQLRepository implements PublisherDAO {
    private static final String PUBLISHER_TABLE_NAME = "bookapp.publishers";
    private static final Logger logger = LoggerFactory.getLogger(PublisherSQLRepository.class);
    public static final PublisherSQLRepository INSTANCE = new PublisherSQLRepository();

    private JdbcConnectionPool connectionPool;

    public static PublisherSQLRepository getInstance() {
        return INSTANCE;
    }

    private PublisherSQLRepository() {
        connectionPool = JdbcConnectionPool.create(Constants.DATABASE_URL,
                Constants.DATABASE_USER_NAME, Constants.DATABASE_USER_PASSWORD);
    }

    @Override
    public void add(Publisher item) {
        String addAuthorQuery = String.format("insert into %s (%s,%s ) values(?,?)",
                PUBLISHER_TABLE_NAME,
                PublisherTableColumnName.ID.toString(),
                PublisherTableColumnName.NAME.toString());

        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(addAuthorQuery);
            statement.setInt(1, item.getId());
            statement.setString(2, item.getName());
            statement.execute();
        } catch (SQLException e) {
            logger.info("db add query drop down:" + e.getMessage());
        }
    }

    @Override
    public void remove(Publisher item) {
        remove(item.getId());
    }

    public Publisher remove(int id) {
        Publisher result = getPublisherById(id);
        String query = String.format("delete from %s where id = ?", PUBLISHER_TABLE_NAME);
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            logger.info("db remove query drop down:" + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Publisher> getList() {
        String getAllPublishers = String.format("select * from %s ", PUBLISHER_TABLE_NAME);

        ResultSet resultSetPublisher;

        List<Publisher> result = new ArrayList<>();

        try (Connection conn = connectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            resultSetPublisher = statement.executeQuery(getAllPublishers);

            while (resultSetPublisher.next()) {
                Integer id = resultSetPublisher.getInt(PublisherTableColumnName.ID.toString());
                String name = resultSetPublisher.getString(PublisherTableColumnName.NAME.toString());

                Publisher publisher = new Publisher(id, name);
                result.add(publisher);
            }
        } catch (SQLException e) {
            logger.info("db view all query drop down:" + e.getMessage());
        }
        return result;
    }

    public Publisher getPublisherById(int id) {
        String query = String.format("select * from %s where %s = ?", PUBLISHER_TABLE_NAME, PublisherTableColumnName.ID);
        String name = "";
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSetPublisher = statement.executeQuery();
            resultSetPublisher.next();
            name = resultSetPublisher.getString(PublisherTableColumnName.NAME.toString());
        } catch (SQLException e) {
            logger.info("db add query drop down:" + e.getMessage());
            return null;
        }
        Publisher publisher = new Publisher(id, name);
        return publisher;
    }

    @Override
    public void setItem(int id, Publisher item) {
        String setPublisherQuery = String.format("update %s set %s = ?,%s=? where %s =?",
                PUBLISHER_TABLE_NAME,
                PublisherTableColumnName.ID,
                PublisherTableColumnName.NAME,
                PublisherTableColumnName.ID);
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(setPublisherQuery);
            statement.setInt(1, item.getId());
            statement.setString(2, item.getName());
            statement.setInt(3, id);
            statement.execute();
        } catch (SQLException e) {
            logger.info("db add query drop down:" + e.getMessage());
        }
    }
}
