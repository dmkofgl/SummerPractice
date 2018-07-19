package com.books.utils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DatabaseCreator {
    private static final String CREATE_DIRECTORY_PATH = "dbScripts/Create_tables/";
    private static final String ALTER_DIRECTORY_PATH = "dbScripts/Alter_tables/";
    private static final String PERSIST_DIRECTORY_PATH = "dbScripts/Persist_tables/";
    private static final String SCHEME_NAME = "BOOKAPP";
    DataSource dataSource;

    public DatabaseCreator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create() throws FileNotFoundException {
        try (Connection conn = dataSource.getConnection()) {
            ResultSet resultSet = conn.getMetaData().getSchemas();
            while (resultSet.next()) {
                String schemaName = resultSet.getString(1).toLowerCase();
                if (schemaName.equals(SCHEME_NAME.toLowerCase())) {
                    return;
                }
            }
            createScheme();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createScheme() throws FileNotFoundException {
        String CreateTables = getFile(CREATE_DIRECTORY_PATH + "Books.sql");
        CreateTables += getFile(CREATE_DIRECTORY_PATH + "Authors.sql");
        CreateTables += getFile(CREATE_DIRECTORY_PATH + "Publishers.sql");
        CreateTables += getFile(CREATE_DIRECTORY_PATH + "Book_author.sql");
        CreateTables += getFile(CREATE_DIRECTORY_PATH + "Create_clients.sql");

        String alterTables = getFile(ALTER_DIRECTORY_PATH + "books.sql");

        String persistTables = getFile(PERSIST_DIRECTORY_PATH + "persist_authors.sql");
        persistTables += getFile(PERSIST_DIRECTORY_PATH + "persist_publishers.sql");
        persistTables += getFile(PERSIST_DIRECTORY_PATH + "persist_books.sql");
        persistTables += getFile(PERSIST_DIRECTORY_PATH + "persist_book_author.sql");
        persistTables += getFile(PERSIST_DIRECTORY_PATH + "persist_clients.sql");

        try (Connection conn = dataSource.getConnection()) {
            Statement createSchemeStatement = conn.createStatement();
            createSchemeStatement.executeUpdate("create schema " + SCHEME_NAME.toString());
            Statement statement = conn.createStatement();
            statement.executeUpdate(CreateTables);
            statement.executeUpdate(alterTables);
            statement.executeUpdate(persistTables);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getFile(String fileName) throws FileNotFoundException {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            scanner.close();

            return result.toString();

        }
    }

    public void drop(String schemaName) {
        String dropSchema = "drop schema if exists " + schemaName;

        try (Connection conn = dataSource.getConnection()) {
            Statement s = conn.createStatement();
            s.executeUpdate(dropSchema);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
