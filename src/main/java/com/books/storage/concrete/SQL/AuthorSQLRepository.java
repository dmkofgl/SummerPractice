package com.books.storage.concrete.SQL;

import com.books.entities.Person;
import com.books.storage.abstracts.AuthorDAO;
import com.books.storage.abstracts.Repository;
import com.books.utils.AuthorTableColomnName;
import com.books.utils.Constants;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorSQLRepository implements AuthorDAO {
    private static final String AUTHOR_TABLE_NAME = "bookapp.authors";

    private static final Logger logger = LoggerFactory.getLogger(AuthorSQLRepository.class);
    public static final AuthorSQLRepository INSTANCE = new AuthorSQLRepository();
    private JdbcConnectionPool connectionPool;

    public static AuthorSQLRepository getInstance() {
        return INSTANCE;
    }

    private AuthorSQLRepository() {
        connectionPool = JdbcConnectionPool.create(Constants.DATABASE_URL,
                Constants.DATABASE_USER_NAME, Constants.DATABASE_USER_PASSWORD);
    }

    @Override
    public void add(Person item) {
        String addAuthorQuery = String.format("insert into %s (%s,%s,%s ) values(?, ?, ?)",
                AUTHOR_TABLE_NAME,
                AuthorTableColomnName.ID.toString(),
                AuthorTableColomnName.FIRST_NAME.toString(),
                AuthorTableColomnName.LAST_NAME.toString(),
                item.getId(),
                item.getFirstName(),
                item.getLastName());
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(addAuthorQuery);
            statement.setInt(1, item.getId());
            statement.setString(2, item.getFirstName());
            statement.setString(3, item.getLastName());
            statement.execute();
        } catch (SQLException e) {
            logger.info("db add query drop down:" + e.getMessage());
        }
    }

    @Override
    public void remove(Person item) {
        remove(item.getId());
    }

    @Override
    public Person remove(int id) {
        Person result = getAuthorById(id);
        String query = String.format("delete from %s where %s = ?", AUTHOR_TABLE_NAME, AuthorTableColomnName.ID);
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
    public List<Person> getList() {
        String getAllAuthors = String.format("select * from %s ", AUTHOR_TABLE_NAME);

        ResultSet resultSetAuthors;

        List<Person> result = new ArrayList<>();

        try (Connection conn = connectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            resultSetAuthors = statement.executeQuery(getAllAuthors);

            while (resultSetAuthors.next()) {
                Integer id = resultSetAuthors.getInt(AuthorTableColomnName.ID.toString());
                String firstName = resultSetAuthors.getString(AuthorTableColomnName.FIRST_NAME.toString());
                String lastName = resultSetAuthors.getString(AuthorTableColomnName.LAST_NAME.toString());

                Person author = new Person(id, firstName, lastName);
                result.add(author);
            }
        } catch (SQLException e) {
            logger.info("db remove query drop down:" + e.getMessage());
        }
        return result;
    }

    //return Person before modification, or void?
    @Override
    public void setItem(int id, Person item) {
        String setAuthorQuery = String.format("update %s set %s = ?,%s=?,%s=? where %s =?",
                AUTHOR_TABLE_NAME,
                AuthorTableColomnName.ID,
                AuthorTableColomnName.FIRST_NAME,
                AuthorTableColomnName.LAST_NAME,
                AuthorTableColomnName.ID);
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(setAuthorQuery);
            statement.setInt(1, item.getId());
            statement.setString(2, item.getFirstName());
            statement.setString(3, item.getLastName());
            statement.setInt(4, id);
            statement.execute();
        } catch (SQLException e) {
            logger.info("db add query drop down:" + e.getMessage());
        }
    }

    public Person getAuthorById(int id) {
        String getAuthorByIdQuery = String.format("select * from %s where %s = ?", AUTHOR_TABLE_NAME, AuthorTableColomnName.ID);
        String firstName = "";
        String lastName = "";
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(getAuthorByIdQuery);
            statement.setInt(1, id);
            ResultSet resultSetAuthors = statement.executeQuery();

            resultSetAuthors.next();
            firstName = resultSetAuthors.getString(AuthorTableColomnName.FIRST_NAME.toString());
            lastName = resultSetAuthors.getString(AuthorTableColomnName.LAST_NAME.toString());
        } catch (SQLException e) {
            logger.info("db add query drop down:" + e.getMessage());
            return null;
        }
        Person author = new Person(id, firstName, lastName);
        return author;

    }
}
