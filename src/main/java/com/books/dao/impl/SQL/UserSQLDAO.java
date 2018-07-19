package com.books.dao.impl.SQL;

import com.books.dao.abstracts.UserDAO;
import com.books.entities.User;
import com.books.utils.UserTableColumnName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserSQLDAO implements UserDAO {
    private static final String USER_TABLE_NAME = "bookapp.client";
    private static final Logger logger = LoggerFactory.getLogger(UserSQLDAO.class);

    private JdbcTemplate jdbcTemplate;


    public UserSQLDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User takeUser(String login, String password) {
        logger.info("Try to take user with login:" + login);
        String query = String.format("select %s from %s where %s = ? and %s = ?", UserTableColumnName.ID.toString(), USER_TABLE_NAME,
                UserTableColumnName.LOGIN, UserTableColumnName.PASSWORD);
        Integer id = jdbcTemplate.queryForObject(query, new Object[]{login, password}, Integer.class);
        return new User(id, login);

    }

    @Override
    public boolean checkExistsUser(String login) {
        String query = String.format("select count(*) from %s where %s = ? ", USER_TABLE_NAME,
                UserTableColumnName.LOGIN);
        int rowCount;
        rowCount = this.jdbcTemplate.queryForObject(query, new Object[]{login}, Integer.class);
        logger.info("Check exists user with login:" + login);
        return rowCount != 0;
    }
}
