package com.books.utils;

import org.h2.jdbcx.JdbcConnectionPool;

public class DatabaseConnector {


    private static final DatabaseConnector INSTANCE = new DatabaseConnector();

    public JdbcConnectionPool getConnectionPool() {
        return connectionPool;
    }

    private final JdbcConnectionPool connectionPool;

    public static DatabaseConnector getInstance() {
        return INSTANCE;
    }

    private DatabaseConnector() {
        connectionPool = JdbcConnectionPool.create(Constants.DATABASE_URL,
                Constants.DATABASE_USER_NAME, Constants.DATABASE_USER_PASSWORD);
    }
}
