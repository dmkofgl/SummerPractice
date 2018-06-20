package com.books.storage.concrete.SQL;

import com.books.entities.Person;
import com.books.storage.abstracts.Repository;
import com.books.utils.AuthorTableColomnName;
import com.books.utils.Constants;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthorSQLRepository implements Repository<Person> {
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
        String addAuthorQuery = String.format("insert into %s (%s,%s,%s ) values(%s,'%s', '%s')",
                AUTHOR_TABLE_NAME,
                AuthorTableColomnName.ID.toString(),
                AuthorTableColomnName.FIRST_NAME.toString(),
                AuthorTableColomnName.LAST_NAME.toString(),
                item.getId(),
                item.getFirstName(),
                item.getLastName());
        try (Connection conn = connectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(addAuthorQuery);
        } catch (SQLException e) {
            logger.info("db add query drop down:" + e.getMessage());
        }
    }

    @Override
    public void remove(Person item) {
        remove(item.getId());
    }

    public Person remove(int id) {
        String query = String.format("delete from %s where id = %s", AUTHOR_TABLE_NAME, id);
        try (Connection conn = connectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.info("db remove query drop down:" + e.getMessage());
        }
        // TODO fix
        return null;
    }

    @Override
    public List<Person> getCollection() {
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

    @Override
    public void setItem(int id, Person item) {

    }

    public Person getAuthorById(int id) {
        String getAuthorByIdQuery = String.format("select * from %s where id = %s", AUTHOR_TABLE_NAME, id);

        ResultSet resultSetAuthors;
        String firstName;
        String lastName;
        try (Connection conn = connectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            resultSetAuthors = statement.executeQuery(getAuthorByIdQuery);
            //wtf
            resultSetAuthors.next();
            firstName = resultSetAuthors.getString(AuthorTableColomnName.FIRST_NAME.toString());
            lastName = resultSetAuthors.getString(AuthorTableColomnName.LAST_NAME.toString());
        } catch (SQLException e) {
            logger.info("db add query drop down:" + e.getMessage());
            return null;
        }
        Person author = new Person(id,firstName,lastName);
        return author;

    }
}
