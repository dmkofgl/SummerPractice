package com.books.storage.concrete.SQL;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.services.PublisherService;
import com.books.storage.abstracts.Repository;
import com.books.utils.BookAuthorTableColumnName;
import com.books.utils.BookTableColumnName;
import com.books.utils.Constants;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BookSQLRepository implements Repository<Book> {
    private static final String BOOK_TABLE_NAME = "books";
    private static final String BOOK_AUTHORS_TABLE_NAME = "book_author";


    private static final Logger logger = LoggerFactory.getLogger(BookSQLRepository.class);
    public static final BookSQLRepository INSTANCE = new BookSQLRepository();
    private JdbcConnectionPool connectionPool;

    public static BookSQLRepository getInstance() {
        return INSTANCE;
    }

    private BookSQLRepository() {
        connectionPool = JdbcConnectionPool.create(Constants.DATABASE_URL,
                Constants.DATABASE_USER_NAME, Constants.DATABASE_USER_PASSWORD);
    }

    @Override
    public void add(Book item) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String queryBook = String.format("insert into %s (%s,%s,%s,%s ) values(%s,'%s', %s,'%s')",
                BOOK_TABLE_NAME,
                BookTableColumnName.ID.toString(),
                BookTableColumnName.NAME.toString(),
                BookTableColumnName.PUBLISHER_ID.toString(),
                BookTableColumnName.BOOKDATE.toString(),
                item.getId(),
                item.getName(),
                item.getPublisher().getId(),
                dateFormat.format(item.getPublishDate()));

        Collection<Person> authors = item.getAuthors();
        String queryBookAuthor = "";
        for (Person author : authors) {
            queryBookAuthor += String.format("insert into %s (%s,%s ) values(%s, %s);",
                    BOOK_AUTHORS_TABLE_NAME,
                    BookAuthorTableColumnName.AUTHOR_ID.toString(),
                    BookAuthorTableColumnName.BOOK_ID.toString(),
                    author.getId(), item.getId());
        }
        try (Connection conn = connectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(queryBook);
            statement.executeUpdate(queryBookAuthor);
        } catch (SQLException e) {
            logger.info("db add query drop down:" + e.getMessage());
        }

    }

    @Override
    public void remove(Book item) {
        remove(item.getId());
    }

    public void remove(int id) {
        String query = String.format("delete from %s where id = %s", BOOK_TABLE_NAME, id);
        try (Connection conn = connectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.info("db remove query drop down:" + e.getMessage());
        }
    }

    @Override
    public List<Book> getCollection() {
        String queryBook = String.format("select * from %s ", BOOK_TABLE_NAME);
        String queryBookAuthors = String.format("select * from %s ", BOOK_AUTHORS_TABLE_NAME);
        Repository<Person> authorRepository = AuthorSQLRepository.getInstance();
        PublisherSQLRepository publisherRepository = PublisherSQLRepository.getInstance();
        ResultSet resultSetBooks;
        ResultSet resultSetBookAuthors;

        List<Book> result = new ArrayList<>();

        try (Connection conn = connectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            Statement statement2 = conn.createStatement();
            resultSetBooks = statement.executeQuery(queryBook);
            resultSetBookAuthors = statement2.executeQuery(queryBookAuthors);

            while (resultSetBooks.next()) {
                //prepare date to create book
                Integer id = resultSetBooks.getInt(BookTableColumnName.ID.toString());
                String name = resultSetBooks.getString(BookTableColumnName.NAME.toString());
                Date date = resultSetBooks.getDate(BookTableColumnName.BOOKDATE.toString());
                List<Person> authors = new ArrayList<>();
                Publisher publisher = publisherRepository.getPublisherById(
                        resultSetBooks.getInt(BookTableColumnName.PUBLISHER_ID.toString()));
                while (resultSetBookAuthors.next()) {
                    if (resultSetBookAuthors.getInt(BookAuthorTableColumnName.BOOK_ID.toString()) == id) {
                        Integer authorId = resultSetBookAuthors.getInt(BookAuthorTableColumnName.AUTHOR_ID.toString());
                        authors.add(((AuthorSQLRepository) authorRepository).getAuthorById(authorId));
                    }
                }
                Book book = new Book(id, name, date, publisher, authors);
                result.add(book);
            }
        } catch (SQLException e) {
            logger.info("db get all query drop down:" + e.getMessage());
        } finally {
        }
        return result;
    }

    @Override
    public void setItem(int id, Book item) {

    }
}
