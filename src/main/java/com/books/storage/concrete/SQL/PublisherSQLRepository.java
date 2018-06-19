package com.books.storage.concrete.SQL;

import com.books.entities.Publisher;
import com.books.storage.abstracts.Repository;
import com.books.utils.Constants;
import com.books.utils.PublisherTableColumnName;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PublisherSQLRepository implements Repository<Publisher> {
    private static final String PUBLISHER_TABLE_NAME = "publishers";
    private static final Logger logger = LoggerFactory.getLogger(PublisherSQLRepository.class);
    public static final PublisherSQLRepository INSTANCE = new PublisherSQLRepository();

    private JdbcConnectionPool connectionPool;

    public static PublisherSQLRepository getInstance() {
        return INSTANCE;
    }

    public PublisherSQLRepository() {
        this.connectionPool = connectionPool = JdbcConnectionPool.create(Constants.DATABASE_URL,
                Constants.DATABASE_USER_NAME, Constants.DATABASE_USER_PASSWORD);
    }

    @Override
    public void add(Publisher item) {
        String addAuthorQuery = String.format("insert into %s (%s,%s ) values(%s,'%s')",
                PUBLISHER_TABLE_NAME,
                PublisherTableColumnName.ID.toString(),
                PublisherTableColumnName.NAME.toString(),
                item.getId(),
                item.getName());
        try (Connection conn = connectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(addAuthorQuery);
        } catch (SQLException e) {
            logger.info("db add query drop down:" + e.getMessage());
        }
    }

    @Override
    public void remove(Publisher item) {
        remove(item.getId());
    }

    public void remove(int id) {
        String query = String.format("delete from %s where id = %s", PUBLISHER_TABLE_NAME, id);
        try (Connection conn = connectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.info("db remove query drop down:" + e.getMessage());
        }
    }

    @Override
    public List<Publisher> getCollection() {
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
        } finally {
        }
        return result;
    }

    public Publisher getPublisherById(int id) {
        String query = String.format("select * from %s where id = %s", PUBLISHER_TABLE_NAME, id);
        ResultSet resultSetPublisher;
        String name;
        try (Connection conn = connectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            resultSetPublisher = statement.executeQuery(query);
            //wtf?
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

    }
}
