package com.books.utils;

import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.stereotype.Component;

@Component("DatabaseConnector")
public class DatabaseConnector {

    public JdbcConnectionPool getConnectionPool() {
        return connectionPool;
    }

    private final JdbcConnectionPool connectionPool;

    private DatabaseConnector() {
        connectionPool = JdbcConnectionPool.create(Constants.DATABASE_URL,
                Constants.DATABASE_USER_NAME, Constants.DATABASE_USER_PASSWORD);
    }
}
