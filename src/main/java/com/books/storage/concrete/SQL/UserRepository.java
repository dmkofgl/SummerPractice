package com.books.storage.concrete.SQL;

import com.books.entities.User;
import org.h2.jdbcx.JdbcConnectionPool;
import com.books.storage.abstracts.UserDAO;
import com.books.entities.Publisher;
import com.books.storage.abstracts.Repository;
import com.books.utils.Constants;
import com.books.utils.PublisherTableColumnName;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements UserDAO {
    private static final String USER_TABLE_NAME = "bookapp.client";
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private JdbcConnectionPool connectionPool;
    public static final UserRepository INSTANCE = new UserRepository();

    public static UserRepository getInstance() {
        return INSTANCE;
    }

    public UserRepository() {
        connectionPool = JdbcConnectionPool.create(Constants.DATABASE_URL,
                Constants.DATABASE_USER_NAME, Constants.DATABASE_USER_PASSWORD);
    }

    @Override
    public User takeUser(String login, String password) {
//TODO create and use Enum
        String query = String.format("select * from %s where %s = ? and %s = ?", USER_TABLE_NAME, "login", "password");
        User user = null;
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSetUser = statement.executeQuery();
            //TODO if not exist?
            if (resultSetUser.next()) {
                int id = resultSetUser.getInt("id");
                user = new User(id, login);
            }

        } catch (SQLException e) {
            logger.info("db take query drop down:" + e.getMessage());
            return null;
        }
        return user;
    }
}
