package com.books.storage.concrete.SQL;

import com.books.entities.User;
import com.books.utils.DatabaseConnector;
import com.books.utils.UserTableColumnName;
import org.h2.jdbcx.JdbcConnectionPool;
import com.books.storage.abstracts.UserDAO;
import com.books.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSQLDAO implements UserDAO {
    private static final String USER_TABLE_NAME = "bookapp.client";
    private static final Logger logger = LoggerFactory.getLogger(UserSQLDAO.class);

    private JdbcConnectionPool connectionPool;

    public static final UserSQLDAO INSTANCE = new UserSQLDAO();

    public static UserSQLDAO getInstance() {
        return INSTANCE;
    }

    public UserSQLDAO() {
        connectionPool = DatabaseConnector.getInstance().getConnectionPool();
    }

    @Override
    public User takeUser(String login, String password) {

        String query = String.format("select * from %s where %s = ? and %s = ?", USER_TABLE_NAME,
                UserTableColumnName.LOGIN, UserTableColumnName.PASSWORD);
        User user = null;
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSetUser = statement.executeQuery();
            if (resultSetUser.next()) {
                int id = resultSetUser.getInt("id");
                user = new User(id, login);
            }

        } catch (SQLException e) {
            logger.info("db take query drop down:" + e.getMessage());
        }
        return user;
    }

    @Override
    public boolean checkExistsUser(String login) {

        String query = String.format("select * from %s where %s = ? ", USER_TABLE_NAME,
                UserTableColumnName.LOGIN);
        Boolean result = false;
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSetUser = statement.executeQuery();
            result = resultSetUser.next();

        } catch (SQLException e) {
            logger.info("db take query drop down:" + e.getMessage());
        }
        return result;
    }
}
