package com.books.storage.concrete;

import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookSQLRepository {
    private static final Logger logger = LoggerFactory.getLogger(BookSQLRepository.class);

    private static void exampleDBConnection() {
        String url = "jdbc:h2:~/test;";
        // "INIT=RUNSCRIPT FROM 'classpath:dbScript/persist_tables/persist_books.sql'";

        JdbcConnectionPool cp = JdbcConnectionPool.
                create(url, "sa", "");
        try (Connection conn = cp.getConnection()) {
            //read data
            String query = "select * from books";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet);
            }
            //insert data
            query = "RUNSCRIPT FROM 'persist_books.sql'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.info("db query drop down:" + e.getMessage());
        } finally {
            cp.dispose();
        }

    }
}
